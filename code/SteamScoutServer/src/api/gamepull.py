'''
Created on Mar 22, 2020

@author: luke, Nathan Lightholder
'''

from api.apihandler import APIHandler

class GamePull(object):
    '''
    This class handles pulling in all of the steam
    games and putting them into the dataset.
    '''

    def __init__(self):
        '''
        Constructs the new GamePull object. Since the GamePull is intended
        to be run on its own, it has its own apihandler. The limiting functionality
        is redundant here because the call is only ever made once; however, the 
        handler is still used so as to not repeat code.
        '''
        self.api = APIHandler(timer_reset_seconds=300, limit=175)

    def pull_games(self, test_mode=False):
        '''
        Pulls the game information. If test_mode is True, then two test games are returned
        to simulate what would be returned from the api. If test_mode is False, then the 
        actual api is called with the real results from Steam.
        
        @param test_mode : whether or not to pull games in test mode
        @return the steam games.
        '''
        service = _FakeGamePullService() if test_mode else _GamePullService(self.api)
        return service.make_pull()
    
    def cleanup(self):
        self.api.stop_timer()

class _FakeGamePullService(object):
    '''
    This is the fake service used for testing. It returns
    fake data that simulates what would be returned for the
    non test mode run.
    '''
    
    def make_pull(self):
        '''
        Returns the fake steam data.
        
        @return: the fake steam data.
        '''
        return {'660010': 'test2', '660130': 'test3'}
        
class _GamePullService(object):
    '''
    The actual service used for non testing purposes. Actually makes
    the api call and gets the results from steam.
    '''
    
    def __init__(self, api):
        '''
        Constructs the game pull service with the specified api
        handler. This handler is used to actually make the json request
        and ensures limits are not exceeded.
        
        @param api : the api.apihandler.APIHandler object that handles the request.
        '''
        self.api = api
    
    def make_pull(self):
        '''
        Makes the steam api pull and formats a dictionary from the results.
        The resulting dictionary is the form {'game-steamid': 'game-title', ...}
        
        @return: the formatted dictionary that contains the steam game results.
        '''
        url = 'http://api.steampowered.com/ISteamApps/GetAppList/v0001/'
        results = self.api.make_request(url)
        
        results_json = results['json']
        if results_json is None:
            return None
        
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
        