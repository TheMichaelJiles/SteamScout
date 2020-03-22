'''
Created on Mar 21, 2020

@author: luke
'''

import json
import os
from api.gamerequestapi import GameRequestAPI
from api.apihandler import APIHandler

class WatchlistUpdater(object):
    '''
    '''

    def __init__(self):
        self.api = APIHandler(timer_reset_seconds=300, limit=175)

    def perform_updates(self, test_mode=False):
        '''
        '''
        watchlist_filename = 'watchlist_table_test.json' if test_mode else 'watchlist_table.json'
        game_filename = 'game_table_test.json' if test_mode else 'game_table.json'
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', watchlist_filename), 'r') as watchlist_jsonfile:
            watchlist_data = json.load(watchlist_jsonfile)
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', game_filename), 'r') as game_jsonfile:
                game_data = json.load(game_jsonfile)
                for key in watchlist_data:
                    request = GameRequestAPI(watchlist_data[key]['steamid'], self.api)
                    result = request.get_info(test_mode)
                
                    if result != None:
                        game_data[str(watchlist_data[key]['steamid'])]['initialprice'] = result['initialprice']
                        game_data[str(watchlist_data[key]['steamid'])]['actualprice'] = result['actualprice']
                        game_data[str(watchlist_data[key]['steamid'])]['onsale'] = result['onsale']
                
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', game_filename), 'w') as jsonfile:
            json.dump(game_data, jsonfile)
            
        self.api.stop_timer()
            
if __name__ == '__main__':
    updater = WatchlistUpdater()
    updater.perform_updates()