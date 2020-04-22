'''
Created on Mar 18, 2020

@author: luke
'''

import json
from dataupdates.fileaccess import FileAccess
from server_requests.watchlistgamefetcher import WatchlistGameFetcher

class WatchlistRemoval(object):
    '''
    The watchlist removal service is used to remove a game from a 
    user's watchlist.
    '''

    def __init__(self, username, game_steamid):
        '''
        Constructs the watchlist removal service for the account with the 
        specified username and the game with the specified steam id.
        
        @param username : the username of the account to remove the game
        @param game_steamid : the steamid of the game to remove 
        '''
        self.username = username
        self.game_steamid = game_steamid
        
    def process_service(self, test_mode = False):
        '''
        Processes the watchlist removal service. This entails removing the specified
        game from the specified users watchlist. If the value of test_mode is True, then
        the modifications will occur in the table that ends with _test.json. If test_mode
        is False, then the modifications will occur in the table that does not end in _test.json.
        
        @param test_mode : whether or not to run the service in test mode.
        @return: the users new watchlist information after the removal processing.
        '''
        service = _WatchlistRemovalService()
        filename = 'watchlist_table_test.json' if test_mode else 'watchlist_table.json'
        return service.attempt_removal(self.username, self.game_steamid, filename)
        
class _WatchlistRemovalService(object):
    '''
    This is the actual watchlist removal service that operates on the test data file watchlist_table.json.
    It removes a game form a user's watchlist.
    '''
    
    def attempt_removal(self, username, game_steamid, filename):
        '''
        Removes the game from the user's watchlist and commits the changes. Then,
        fetches the new watchlist data after the processed removal and returns it in
        dictionary format.
        
        @param username : the username of the account to remove the game
        @param game_steamid : the steamid of the game to remove
        @param filename : the filename of the table to be used
        
        @return the new watchlist data for the account.
        @see: server_requests.gamefetcher.WatchlistGameFetcher for the returned results.
        '''
        key_to_remove = None
        watchlist_data = FileAccess.read_watchlist_table(lambda jsonfile: self._read_data(jsonfile), filename)
        for key in watchlist_data:
            if watchlist_data[key]['username'] == username and watchlist_data[key]['steamid'] == game_steamid:
                key_to_remove = key
        watchlist_data.pop(key_to_remove, None)
        
        FileAccess.write_watchlist_table(lambda watchlist_data, jsonfile: self._write_data(watchlist_data, jsonfile), filename, watchlist_data) 
        
        watchlist_game_fetcher = WatchlistGameFetcher(username)
        return watchlist_game_fetcher.process_service(test_mode=(filename == 'watchlist_table_test.json'))
    
    def _read_data(self, file):
        return json.load(file)
    
    def _write_data(self, data, file):
        json.dump(data, file)
            