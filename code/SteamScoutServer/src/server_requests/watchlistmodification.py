'''
Created on Mar 19, 2020

@author: Michael
'''
import json
import os
from requests.watchlistgamefetcher import WatchlistGameFetcher

class WatchlistModification(object):
    '''
    classdocs
    '''
    

    def __init__(self, user_name, steam_id, on_sale_selected, price_threshold, target_price_selected):
        '''
        This watchlist modification service takes a username, a steam id for a game, and
        the notification criteria for that game. It will find the specified game in the
        user's watchlist and update the notification criteria accordingly.
        
        @param user_name : string - the user's username.
        @param steam_id : int - the steam id of the game to change.
        @param on_sale_selected : boolean - whether or not the user should be notified
        when the game goes on sale.
        @param price_threshold : double - the price threshold at which the user should be
        notified
        @param target_price_selected : boolean - whether or not the user wants to be notified
        when the game is below the price threshold.
        '''
        self.user_name = user_name
        self.steam_id = steam_id
        self.on_sale_selected = on_sale_selected
        self.target_price_selected = target_price_selected
        self.price_threshold = price_threshold
    
    def process_service(self, test_mode = False):
        '''
        
        '''
        service = _FakeWatchlistModificationService() if test_mode else _WatchlistModificationService()
        return service.make_watchlist_modification(self.user_name, self.steam_id, self.on_sale_selected, self.price_threshold, self.target_price_selected)
    
class _FakeWatchlistModificationService(object):
    '''
    
    '''
    
    def make_watchlist_modification(self, user_name, steam_id, on_sale_selected, price_threshold, target_price_selected):
        '''
        
        '''
        was_modified = False
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as watchlist_file:
            watchlist_test_table = json.load(watchlist_file)
            for key in watchlist_test_table:
                if watchlist_test_table[key]['username'] == user_name and watchlist_test_table[key]['steamid'] == steam_id:
                    watchlist_test_table[key]['targetprice_criteria'] = price_threshold
                    watchlist_test_table[key]['onsale_selected'] = on_sale_selected
                    watchlist_test_table[key]['targetprice_selected'] = target_price_selected
                    was_modified = True
                    
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'w') as write_watchlist_file:
            json.dump(watchlist_test_table, write_watchlist_file)
        watchlist_fetcher = WatchlistGameFetcher(user_name)
        watchlist_data = watchlist_fetcher.process_service(test_mode = True)
        watchlist_data.update({'result' : was_modified})
        return watchlist_data 
        
class _WatchlistModificationService(object):
    '''
    
    '''
    
    def make_watchlist_modification(self, user_name, steam_id, on_sale_selected, price_threshold, target_price_selected):
        '''
        
        '''
        
        
        
        
        