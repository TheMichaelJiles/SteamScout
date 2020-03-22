'''
Created on Mar 18, 2020

@author: luke
'''

import json
import os
from requests.watchlistgamefetcher import WatchlistGameFetcher

class WatchlistRemoval(object):
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
        service = _FakeWatchlistRemovalService() if test_mode else _WatchlistRemovalService()
        return service.attempt_removal(self.username, self.game_steamid)
        
class _FakeWatchlistRemovalService(object):
    '''
    '''
    
    def attempt_removal(self, username, game_steamid):
        '''
        '''
        key_to_remove = None
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as jsonfile:
            watchlist_data = json.load(jsonfile)
            for key in watchlist_data:
                if watchlist_data[key]['username'] == username and watchlist_data[key]['steamid'] == game_steamid:
                    key_to_remove = key
        
        watchlist_data.pop(key_to_remove, None)
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'w') as jsonfile:
            json.dump(watchlist_data, jsonfile)
        
        watchlist_game_fetcher = WatchlistGameFetcher(username)
        return watchlist_game_fetcher.process_service(test_mode=True)
        
class _WatchlistRemovalService(object):
    '''
    '''
    
    def attempt_removal(self, username, game_steamid):
        '''
        '''
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'r') as jsonfile:
            watchlist_data = json.load(jsonfile)
            for key in watchlist_data:
                if watchlist_data[key]['username'] == username and watchlist_data[key]['steamid'] == game_steamid:
                    key_to_remove = key
            watchlist_data.pop(key_to_remove, None)
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'w') as jsonfile:
            json.dump(watchlist_data, jsonfile)
        
        watchlist_game_fetcher = WatchlistGameFetcher(username)
        return watchlist_game_fetcher.process_service(test_mode=False)
            