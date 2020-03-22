'''
Created on Mar 18, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os
from requests.watchlistgamefetcher import WatchlistGameFetcher

class WatchlistAddition(object):
    '''
    
    '''

    def __init__(self, username, game_steamid):
        '''
        
        '''
        self.username = username
        self.game_steamid = game_steamid
        
    def process_service(self, test_mode = False):
        '''
        '''
        service = _FakeWatchlistAdditionService() if test_mode else _WatchlistAdditionService()
        return service.attempt_addition(self.username, self.game_steamid)
        
class _FakeWatchlistAdditionService(object):
    '''
    '''
    
    def attempt_addition(self, username, game_steamid):
        '''
        '''
        does_game_exist = self._does_game_exist(game_steamid)
        not_already_on_watchlist = self._is_not_already_on_watchlist(username, game_steamid)
        
        should_add = not_already_on_watchlist and does_game_exist
        if should_add:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as jsonfile:
                watchlist_data = json.load(jsonfile)
                keys = map(lambda x: int(x), watchlist_data.keys())
                watchlist_data[max(keys) + 1] = {'steamid': game_steamid,
                                                           'username': username,
                                                           'targetprice_criteria': 0.0,
                                                           'onsale_selected': False,
                                                           'targetprice_selected': False}
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'w') as jsonfile:
                json.dump(watchlist_data, jsonfile) 
        
        watchlist_game_fetcher = WatchlistGameFetcher(username)
        results = watchlist_game_fetcher.process_service(test_mode=True)    
        addition_result = {'result': should_add}
        addition_result.update(results)
        return addition_result
    
    def _does_game_exist(self, game_steamid):
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table_test.json'), 'r') as jsonfile:
            game_data = json.load(jsonfile)
            return str(game_steamid) in game_data
    
    def _is_not_already_on_watchlist(self, username, game_steamid):
        not_already_on_watchlist = True
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as jsonfile:
            watchlist_data = json.load(jsonfile)
            for key in watchlist_data:
                if watchlist_data[key]['username'] == username:
                    if watchlist_data[key]['steamid'] == game_steamid:
                        not_already_on_watchlist = False
        return not_already_on_watchlist
        
    
class _WatchlistAdditionService(object):
    '''
    '''
    
    def attempt_addition(self, username, game_steamid):
        '''
        '''
        does_game_exist = self._does_game_exist(game_steamid)
        not_already_on_watchlist = self._is_not_already_on_watchlist(username, game_steamid)
        
        should_add = not_already_on_watchlist and does_game_exist
        if should_add:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'r') as jsonfile:
                watchlist_data = json.load(jsonfile)
                keys = map(lambda x: int(x), watchlist_data.keys())
                watchlist_data[max(keys) + 1] = {'steamid': game_steamid,
                                                           'username': username,
                                                           'targetprice_criteria': 0.0,
                                                           'onsale_selected': False,
                                                           'targetprice_selected': False}
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'w') as jsonfile:
                json.dump(watchlist_data, jsonfile) 
        
        watchlist_game_fetcher = WatchlistGameFetcher(username)
        results = watchlist_game_fetcher.process_service(test_mode=False)    
        
        addition_result = {'result': should_add}
        addition_result.update(results)
        return addition_result
    
    def _does_game_exist(self, game_steamid):
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as jsonfile:
            game_data = json.load(jsonfile)
            return str(game_steamid) in game_data
    
    def _is_not_already_on_watchlist(self, username, game_steamid):
        not_already_on_watchlist = True
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'r') as jsonfile:
            watchlist_data = json.load(jsonfile)
            for key in watchlist_data:
                if watchlist_data[key]['username'] == username:
                    if watchlist_data[key]['steamid'] == game_steamid:
                        not_already_on_watchlist = False
        return not_already_on_watchlist
        
        