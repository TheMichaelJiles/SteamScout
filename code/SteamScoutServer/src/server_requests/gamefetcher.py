'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os

class GameFetcher(object):
    '''
    Provides a service that retrieve's all games in the system.
    '''
        
    def process_service(self, test_mode = False):
        '''
        Attempts to retrieve all games in the system. This service returns a 
        json response object that should be sent back to the client.
        It contains the information that the client is requesting.
        
        @postcondition: process_service()["games"] == [{"steamid": integer,
                                                        "title": string,
                                                        "initialprice": double},
                                                        
                                                        {..},
                                                        
                                                        {..}]
               
        @param test_mode: boolean - Whether or not to run this service in test mode.
                            
        @return: the json response to the client
        '''
        service = _GameFetchingService()
        return service.attempt_fetch_games('game_table_test.json' if test_mode else 'game_table.json')
        
class _GameFetchingService(object):
    '''
    Used for production purposes. When the database is available, 
    then this class is used to perform the GameFetching service.
    '''
    
    def attempt_fetch_games(self, filename):
        '''
        Fetches all games in the system. The data is pulled
        from the json files that do not end in _test.json in the test_data directory.
        
        @return: The json response object.
        '''
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'r') as jsonfile:
            games = json.load(jsonfile)
            list_of_game_dicts = []
            for steamid in games:
                game = {}
                game['steamid'] = int(steamid)
                game.update(games[steamid])
                list_of_game_dicts.append(game)
            return {"games": list_of_game_dicts}

