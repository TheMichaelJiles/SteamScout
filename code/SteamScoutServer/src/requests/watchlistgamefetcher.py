'''
Created on Mar 18, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''
import json
import os

class WatchlistGameFetcher(object):
    '''
    '''

    def __init__(self, username):
        '''
        '''
        self.username = username
        
    def process_service(self, test_mode = False):
        '''
        '''
        service = _FakeWatchlistGameFetchingService() if test_mode else _WatchlistGameFetchingService()
        return service.fetch_games_on_watchlist(self.username)
        
class _FakeWatchlistGameFetchingService(object):
    '''
    '''
    
    def fetch_games_on_watchlist(self, username):
        '''
        '''
        steamids = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as jsonfile:
            watchlist_table = json.load(jsonfile)
            for key in watchlist_table:
                if watchlist_table[key]['username'] == username:
                    steamids.append(watchlist_table[key]['steamid'])
           
        games = []   
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table_test.json'), 'r') as jsonfile:
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
    
class _WatchlistGameFetchingService(object):
    '''
    '''
    
    def fetch_games_on_watchlist(self, username):
        '''
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
                
        