'''
Created on Mar 22, 2020

@author: luke, Nathan Lightholder
'''

import json, os

from api.gamepull import GamePull
from dataupdates.fileaccess import FileAccess

class GameUpdater(object):
    '''
    The game updater pulls all of the games on steam and saves
    them in the game_table.json file. This is a LOT of games. It
    crashed my editor when trying to open it. I ran cat game_table.json
    in the terminal and it showed the correct result so I know it works
    correctly.
    '''

    def fill_game_table(self, test_mode=False):
        '''
        Fills the game table with all of the games on steam. Some of the games
        may be dummy games that do not really exist, and some may be in completely
        different languages, but there is not much we can do about this.
        
        @return True, if IO executes successfully, False otherwise
        '''
        
        puller = GamePull()
        print('Pulling Games From API...')
        games = puller.pull_games(test_mode)
        puller.cleanup()
        if test_mode:
            filename = 'game_table_test.json'
        else:
            filename = 'game_table.json' 
        try:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'r') as jsonfile:
                game_data = FileAccess.read_game_table(lambda jsonfile: self._read_data(jsonfile), filename)
                for steamid in games:
                    game_data[steamid] = {'title': games[steamid],
                                          'initialprice': 0.0,
                                          'actualprice': 0.0,
                                          'onsale': False}
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'w') as jsonfile:
                FileAccess.write_game_table(lambda game_data, jsonfile: self._write_data(game_data, jsonfile), filename, game_data)
                
        except (IOError, TypeError):
            print('Failed to update game table')
            return False
        
        print(f'Completed Loading {len(games)} Games Into ' + os.path.join(os.path.dirname(__file__), '..', 'test_data', filename))
        return True
    
    def _read_data(self, file):
        return json.load(file)
    
    def _write_data(self, data, file):
        json.dump(data, file)
            
if __name__ == '__main__':
    update = GameUpdater()
    update.fill_game_table()