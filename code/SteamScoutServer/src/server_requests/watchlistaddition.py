'''
Created on Mar 18, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os
from dataupdates.fileaccess import FileAccess
from server_requests.watchlistgamefetcher import WatchlistGameFetcher

class WatchlistAddition(object):
    '''
    This is the watchlist addition service. It adds a game to a
    user's watchlist.
    '''

    def __init__(self, username, game_steamid):
        '''
        Constructs the watchlist addition service for the account with the 
        specified username and for the game with the specified id.
        
        @param username : the username of the account to perform the watchlist addition.
        @param game_steamid : the id of the game to add.
        '''
        self.username = username
        self.game_steamid = game_steamid
        
    def process_service(self, test_mode = False):
        '''
        Performs the addition service. Returns the new watchlist for the specified account
        after the addition has been processed. If the value of test_mode is True, then the
        addition is performed to the json files that end in _test.json. If the value of test_mode
        is False, then the addition is performed to the json files that do not end in _test.json.
        
        @param test_mode : whether or not to run the service in test mode.
        @return: the newly modified watchlist after the addition has been processed.
        '''
        service = _WatchlistAdditionService()
        watchlist_path = 'watchlist_table_test.json' if test_mode else 'watchlist_table.json'
        game_table_path = 'game_table_test.json' if test_mode else 'game_table.json'
        return FileAccess.access_file(service.attempt_addition(self.username, self.game_steamid, watchlist_path, game_table_path))
        
class _WatchlistAdditionService(object):
    '''
    This is the actual addition service used for testing. It performs operations on the test data 
    files that do not end in _test.json.
    '''
    
    def attempt_addition(self, username, game_steamid, watchlist_path, game_table_path):
        '''
        Attempts to make the addition for the game with the spcified steamid to the account
        with the specified username. There are a couple of cases in which the game will not 
        be added. However, these cases can be removed if we enforce that they do not happen
        on the client side. For one, if the game that they are trying to add does not exist
        in the game_table, then the game is not added. For two, if they already have the game
        on their watchlist, then it is not added again.
        
        @param username : the username of the account to perform the watchlist addition.
        @param game_steamid : the id of the game to add.
        
        @return: the new watchlist for the user that contains the results after the addition has been processed.
        '''
        does_game_exist = self._does_game_exist(game_steamid, game_table_path)
        not_already_on_watchlist = self._is_not_already_on_watchlist(username, game_steamid, watchlist_path)
        
        should_add = not_already_on_watchlist and does_game_exist
        if should_add:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', watchlist_path), 'r') as jsonfile:
                watchlist_data = json.load(jsonfile)
                keys = map(lambda x: int(x), watchlist_data.keys())
                watchlist_data[max(keys, default = 0) + 1] = {'steamid': game_steamid,
                                                           'username': username,
                                                           'targetprice_criteria': 0.0,
                                                           'onsale_selected': False,
                                                           'targetprice_selected': False}
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', watchlist_path), 'w') as jsonfile:
                json.dump(watchlist_data, jsonfile) 
        
        watchlist_game_fetcher = WatchlistGameFetcher(username)
        results = watchlist_game_fetcher.process_service(test_mode=(watchlist_path == 'watchlist_table_test.json'))    
        
        addition_result = {'result': should_add}
        addition_result.update(results)
        return addition_result
    
    def _does_game_exist(self, game_steamid, game_table_path):
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', game_table_path), 'r') as jsonfile:
            game_data = json.load(jsonfile)
            return str(game_steamid) in game_data
    
    def _is_not_already_on_watchlist(self, username, game_steamid, watchlist_path):
        not_already_on_watchlist = True
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', watchlist_path), 'r') as jsonfile:
            watchlist_data = json.load(jsonfile)
            for key in watchlist_data:
                if watchlist_data[key]['username'] == username:
                    if watchlist_data[key]['steamid'] == game_steamid:
                        not_already_on_watchlist = False
        return not_already_on_watchlist
        
        