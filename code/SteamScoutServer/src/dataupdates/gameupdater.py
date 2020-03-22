'''
Created on Mar 22, 2020

@author: luke
'''

import json, os

from api.gamepull import GamePull

class GameUpdater(object):
    '''
    The game updater pulls all of the games on steam and saves
    them in the game_table.json file. This is a LOT of games. It
    crashed my editor when trying to open it. I ran cat game_table.json
    in the terminal and it showed the correct result so I know it works
    correctly.
    '''

    def fill_game_table(self):
        '''
        Fills the game table with all of the games on steam. Some of the games
        may be dummy games that do not really exist, and some may be in completely
        different languages, but there is not much we can do about this.
        '''
        puller = GamePull()
        print('Pulling Games From API')
        games = puller.pull_games(test_mode=False)
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as jsonfile:
            game_data = json.load(jsonfile)
            for steamid in games:
                game_data[steamid] = {'title': games[steamid],
                                      'initialprice': 0.0,
                                      'actualprice': 0.0,
                                      'onsale': False}
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'w') as jsonfile:
            json.dump(game_data, jsonfile)
        print(f'Completed Loading {len(games)} Games Into test_data/game_table.json')
            
if __name__ == '__main__':
    update = GameUpdater()
    update.fill_game_table()