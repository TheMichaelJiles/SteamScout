'''
Created on Mar 19, 2020

@author: Michael Jiles
'''
import json
from dataupdates.fileaccess import FileAccess
from server_requests.watchlistgamefetcher import WatchlistGameFetcher

class WatchlistModification(object):
    '''
    Handles modifying the notification criteria of a game on a user's watchlist.
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
        Processes a watchlist modification request.If test mode is false, then use the real
        WatchlistModification class. Otherwise, use the test class.
        '''
        service = _WatchlistModificationService()
        filename = 'watchlist_table_test.json' if test_mode else 'watchlist_table.json'
        return service.make_watchlist_modification(self.user_name, self.steam_id, self.on_sale_selected, self.price_threshold, self.target_price_selected, filename)
        
class _WatchlistModificationService(object):
    '''
    Class for executing a watchlist modification service.
    '''
    
    def make_watchlist_modification(self, user_name, steam_id, on_sale_selected, price_threshold, target_price_selected, filename):
        ''''
        Takes in username, steam id, whether or not the user wants to be notified
        when the game goes on sale, price threshold at which the user should be notified, and
        whether or not the price threshold should be acknowledged. The service will open 
        the watchlist_table.json file, load it, and then search for the game related 
        to the username and steam id. Then the watchlist will update the values of the table
        to be those passed in, and finally return the new watchlist table including whether or
        not the watchlist table was altered.
        
        @param user_name : string - the user's username.
        @param steam_id : int - the steam id of the game to change.
        @param on_sale_selected : boolean - whether or not the user should be notified
        when the game goes on sale.
        @param price_threshold : double - the price threshold at which the user should be
        notified
        @param target_price_selected : boolean - whether or not the user wants to be notified
        when the game is below the price threshold.
        @param filename : the filename of the table to be used
        
        @return the updated watchlist data including if the table was altered.
        '''
        was_modified = False
        watchlist_table = FileAccess.read_watchlist_table(lambda watchlist_file: self._read_data(watchlist_file), filename)
        for key in watchlist_table:
            if watchlist_table[key]['username'] == user_name and watchlist_table[key]['steamid'] == steam_id:
                watchlist_table[key]['targetprice_criteria'] = price_threshold
                watchlist_table[key]['onsale_selected'] = on_sale_selected
                watchlist_table[key]['targetprice_selected'] = target_price_selected
                was_modified = True
                    
        FileAccess.write_watchlist_table(lambda watchlist_table, write_watchlist_file: self._write_data(watchlist_table, write_watchlist_file), filename, watchlist_table) 
        watchlist_fetcher = WatchlistGameFetcher(user_name)
        watchlist_data = watchlist_fetcher.process_service(test_mode = (filename == 'watchlist_table_test.json'))
        watchlist_data.update({'result' : was_modified})
        return watchlist_data 
    
    def _read_data(self, file):
        return json.load(file)
    
    def _write_data(self, data, file):
        json.dump(data, file)