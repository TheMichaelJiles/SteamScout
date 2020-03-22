'''
Created on Mar 22, 2020

@author: luke
'''

from api.apihandler import APIHandler

class GamePull(object):
    '''
    '''

    def __init__(self):
        self.api = APIHandler(timer_reset_seconds=300, limit=175)

    def pull_games(self, test_mode=False):
        '''
        '''
        service = _FakeGamePullService() if test_mode else _GamePullService(self.api)
        return service.make_pull()

class _FakeGamePullService(object):
    '''
    '''
    
    def make_pull(self):
        '''
        '''
        return {'660010': 'test2', '660130': 'test3'}
        
class _GamePullService(object):
    '''
    '''
    
    def __init__(self, api):
        self.api = api
    
    def make_pull(self):
        '''
        '''
        url = 'http://api.steampowered.com/ISteamApps/GetAppList/v0001/'
        results = self.api.make_request(url)
        
        results_json = results['json']
        if 'applist' not in results_json:
            return None
        
        applist = results_json['applist']
        if 'apps' not in applist:
            return None
        
        apps = applist['apps']
        if 'app' not in apps:
            return None
        
        games = apps['app']
        game_dict = {}
        for game in games:
            game_dict[str(game['appid'])] = game['name']
            
        return game_dict
        