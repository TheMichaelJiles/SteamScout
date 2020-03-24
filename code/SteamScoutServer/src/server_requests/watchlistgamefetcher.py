'''
Created on Mar 18, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''
import json
import os

class WatchlistGameFetcher(object):
    '''
    This service fetches all games on a user's watchlist and returns 
    them in dictionary format.
    '''

    def __init__(self, username):
        '''
        Constructs the WatchlistGameFetcher for the account with the specified username.
        
        @param username : the username of the desired account.
        '''
        self.username = username
        
    def process_service(self, test_mode = False):
        '''
        Processes the service. Returns all of the watchlist games for the specified user
        in dictionary format. If the value of test_mode is True, then the data is taken
        from the test files that end in _test.json. If the value of test_mode is False,
        then the data is taken from the test files that do not end in _test.json.
        
        @param test_mode : whether or not to run the service in test mode.
        @return: the dictionary that contains all games on the specified users watchlist.
        '''
        service = _FakeWatchlistGameFetchingService() if test_mode else _WatchlistGameFetchingService()
        return service.fetch_games_on_watchlist(self.username)
        
class _FakeWatchlistGameFetchingService(object):
    '''
    This is the fake watchlist fetching service that pulls data from the _test.json files.
    '''
    
    def fetch_games_on_watchlist(self, username):
        '''
        Fetches all games on the specified username's watchlist. The returned dictionary is of the format
        {'username': username, 'games_on_watchlist': [{'steamid': steamid,
                                                       'title': title,
                                                       'initialprice': initialprice,
                                                       'actualprice': actualprice,
                                                       'onsale': onsale,
                                                       'targetprice_criteria': targetprice_criteria,
                                                       'onsale_selected': onsale_selected,
                                                       'targetprice_selected': targetprice_selected}, ...]}
                                                       
        @param username : the username of the account to get the watchlist
        @return: the dictionary in the format described above.
        '''
        watchlist_info = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as jsonfile:
            watchlist_table = json.load(jsonfile)
            for key in watchlist_table:
                if watchlist_table[key]['username'] == username:
                    watchlist_info.append({'steamid': watchlist_table[key]['steamid'],
                                     'targetprice_criteria': watchlist_table[key]['targetprice_criteria'],
                                     'onsale_selected': watchlist_table[key]['onsale_selected'],
                                     'targetprice_selected': watchlist_table[key]['targetprice_selected']})
           
        games = []   
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table_test.json'), 'r') as jsonfile:
            game_data = json.load(jsonfile)
            for info in watchlist_info:
                current_game = {}
                current_game['steamid'] = info['steamid']
                current_game['title'] = game_data[str(info['steamid'])]['title']
                current_game['initialprice'] = game_data[str(info['steamid'])]['initialprice']
                current_game['actualprice'] = game_data[str(info['steamid'])]['actualprice']
                current_game['onsale'] = game_data[str(info['steamid'])]['onsale']
                current_game['targetprice_criteria'] = info['targetprice_criteria']
                current_game['onsale_selected'] = info['onsale_selected']
                current_game['targetprice_selected'] = info['targetprice_selected']
                games.append(current_game)
        return {'username': username, 'games_on_watchlist': games}
    
class _WatchlistGameFetchingService(object):
    '''
    This is the actual watchlist fetching service that pulls data from the non _test.json files.
    '''
    
    def fetch_games_on_watchlist(self, username):
        '''
        Fetches all games on the specified username's watchlist. The returned dictionary is of the format
        {'username': username, 'games_on_watchlist': [{'steamid': steamid,
                                                       'title': title,
                                                       'initialprice': initialprice,
                                                       'actualprice': actualprice,
                                                       'onsale': onsale,
                                                       'targetprice_criteria': targetprice_criteria,
                                                       'onsale_selected': onsale_selected,
                                                       'targetprice_selected': targetprice_selected}, ...]}
                                                       
        @param username : the username of the account to get the watchlist
        @return: the dictionary in the format described above.
        '''
        steamids = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'r') as jsonfile:
            watchlist_table = json.load(jsonfile)
            for key in watchlist_table:
                if watchlist_table[key]['username'] == username:
                    steamids.append(watchlist_table[key]['steamid'])
           
        games = []   
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as jsonfile:
            game_data = json.load(jsonfile)
            for steamid in steamids:
                current_game = {}
                current_game['steamid'] = steamid
                current_game['title'] = game_data[str(steamid)]['title']
                current_game['initialprice'] = game_data[str(steamid)]['initialprice']
                current_game['actualprice'] = game_data[str(steamid)]['actualprice']
                current_game['onsale'] = game_data[str(steamid)]['onsale']
                games.append(current_game)
        return {'username': username, 'games_on_watchlist': games}
                
        