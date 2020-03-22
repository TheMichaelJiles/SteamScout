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
    The watchlist updater looks at all games on all watchlists - meaning all stored
    user's watchlists. Then, for each game not already updated, fetches new updated information and stores
    that information for that game in the game table.
    '''

    def __init__(self):
        '''
        Constructs the updater with its own api handler. This is to ensure
        that the api call limit is not exceeded.
        '''
        self.api = APIHandler(timer_reset_seconds=300, limit=175)

    def perform_updates(self, test_mode=False):
        '''
        Performs the updates. Reads in all data from the watchlist and game tables. If test_mode
        is true, then it is read from the _test.json files instead. For each game in the watchlist
        table, if it has not already been updated, then it gets the new information and updates it.
        
        @param test_mode : Whether or not to run the update in test mode.
        '''
        watchlist_filename = 'watchlist_table_test.json' if test_mode else 'watchlist_table.json'
        game_filename = 'game_table_test.json' if test_mode else 'game_table.json'
        updated_ids = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', watchlist_filename), 'r') as watchlist_jsonfile:
            watchlist_data = json.load(watchlist_jsonfile)
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', game_filename), 'r') as game_jsonfile:
                game_data = json.load(game_jsonfile)
                for key in watchlist_data:
                    current_steamid = watchlist_data[key]['steamid']
                    if current_steamid in updated_ids:
                        continue
                    updated_ids.append(current_steamid)
                    request = GameRequestAPI(current_steamid, self.api)
                    result = request.get_info(test_mode)
                    if result != None:
                        game_data[str(current_steamid)]['initialprice'] = result['initialprice']
                        game_data[str(current_steamid)]['actualprice'] = result['actualprice']
                        game_data[str(current_steamid)]['onsale'] = result['onsale']
                
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', game_filename), 'w') as jsonfile:
            json.dump(game_data, jsonfile)
            
        self.api.stop_timer()
            
if __name__ == '__main__':
    updater = WatchlistUpdater()
    updater.perform_updates(test_mode=False)
    
    