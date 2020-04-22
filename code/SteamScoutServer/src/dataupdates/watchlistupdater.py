'''
Created on Mar 21, 2020

@author: luke
'''

import json
import os

from dataupdates.fileaccess import FileAccess
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
        updated_ids = []
        watchlist_data = FileAccess.read_watchlist_table(lambda watchlist_jsonfile: self._read_data(watchlist_jsonfile), watchlist_filename)
        for key in watchlist_data:
            current_steamid = watchlist_data[key]['steamid']
            if current_steamid in updated_ids:
                continue
            updated_ids.append(current_steamid)
            self.perform_game_update(current_steamid, test_mode)
            
        self.api.stop_timer()
        
    def perform_game_update(self, steamid, test_mode=False):
        game_filename = 'game_table_test.json' if test_mode else 'game_table.json'
        game_data = FileAccess.read_game_table(lambda game_jsonfile: self._read_data(game_jsonfile), game_filename)
        request = GameRequestAPI(steamid, self.api)
        result = request.get_info(test_mode)
        if result != None:
            game_data[str(steamid)]['initialprice'] = result['initialprice']
            game_data[str(steamid)]['actualprice'] = result['actualprice']
            game_data[str(steamid)]['onsale'] = result['onsale']
        FileAccess.write_game_table(lambda game_data, jsonfile: self._write_data(game_data, jsonfile), game_filename, game_data)
        
    def _read_data(self, file):
        return json.load(file)
    
    def _write_data(self, data, file):
        json.dump(data, file)
            
if __name__ == '__main__':
    updater = WatchlistUpdater()
    updater.perform_updates(test_mode=False)
    
    