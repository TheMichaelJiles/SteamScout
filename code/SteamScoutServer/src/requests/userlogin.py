'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os

class UserLogin(object):
    '''
    Provides a service that validate's whether a user is
    valid. If the user is valid, then all of the games on 
    the user's watchlist are returned as well.
    '''

    def __init__(self, user_name, password):
        '''
        Initializes this validator with the given username
        and password pair.
        
        @param user_name : string - The username.
        @param password : string - The password.
        '''
        self.user_name = user_name
        self.password = password
        
    def process_service(self, test_mode = False):
        '''
        Attempts to login the user. This service returns a 
        json response object that should be sent back to the client.
        It contains the information that the client is requesting.
        
        @postcondition: if the username is not found in the system, then
                        process_service()["result"] == False and len(process_service()["watchlist"]) == 0
                        
                        if the password does not match the username in the
                        system for the specified user, then
                        process_service()["result"] == False and len(process_service()["watchlist"]) == 0
                        
                        if the username is found and the password is a match,
                        then process_service()["result"] = True and process_service()["watchlist"] contains
                        a list of game dictionaries for each game in the user's watchlist.
                            - The game dictionaries have the following keys: steamid, targetprice, onsale, belowtarget, title, and initialprice
                            
        @param test_mode: boolean - Whether or not to run this service in test mode.
                            
        @return: the json response to the client
        '''
        service = _FakeUserLoginService() if test_mode else _UserLoginService()
        return service.attempt_login(self.user_name, self.password)
        
class _FakeUserLoginService(object):
    '''
    Used for testing purposes. When the database is not available, 
    or when performing tests, then this class is used to perform the 
    UserLogin service.
    '''
    
    def attempt_login(self, user_name, password):
        '''
        Fetches the watchlist data for the given username and password
        pair. The data is retrieved from the json files within the SteamScoutServer/test_data
        directory.
        
        @param user_name : string - The username for the login.
        @param password : string - The password for the login.
        
        @return: The json response object.
        '''
        is_valid = False
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table_test.json'), 'r') as user_json:
            test_data = json.load(user_json)
            for element in test_data:
                if element['username'] == user_name and element['password'] == password:
                    is_valid = True
                    break
                    
        watchlist = []
        if is_valid:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as watchlist_json:
                with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table_test.json'), 'r') as game_json:
                    watchlist_data = json.load(watchlist_json)
                    game_data = json.load(game_json)
                    for item in watchlist_data:
                        if item['username'] == user_name:
                            current_game = {}
                            current_game["steamid"] = item["steamid"]
                            current_game["targetprice_criteria"] = item["targetprice_criteria"]
                            current_game["onsale_selected"] = item["onsale_selected"]
                            for game in game_data:
                                if game["steamid"] == current_game["steamid"]:
                                    current_game["title"] = game["title"]
                                    current_game["initialprice"] = game["initialprice"]
                            watchlist.append(current_game)
            
        json_response = {"result": is_valid, "watchlist": watchlist}
        return json_response
    
class _UserLoginService(object):
    '''
    Used for production purposes. When the database is available, 
    then this class is used to perform the UserLogin service.
    '''
    
    def attempt_login(self, user_name, password):
        '''
        Fetches the watchlist data for the given username and password
        pair. The data is retrieved from the data stored in the database.
        
        @param user_name : string - The username for the login.
        @param password : string - The password for the login.
        
        @return: The json response object.
        '''
        is_valid = False
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table.json'), 'r') as user_json:
            test_data = json.load(user_json)
            for element in test_data:
                if element['username'] == user_name and element['password'] == password:
                    is_valid = True
                    break
                    
        watchlist = []
        if is_valid:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'r') as watchlist_json:
                with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as game_json:
                    watchlist_data = json.load(watchlist_json)
                    game_data = json.load(game_json)
                    for item in watchlist_data:
                        if item['username'] == user_name:
                            current_game = {}
                            current_game["steamid"] = item["steamid"]
                            current_game["targetprice_criteria"] = item["targetprice_criteria"]
                            current_game["onsale_selected"] = item["onsale_selected"]
                            for game in game_data:
                                if game["steamid"] == current_game["steamid"]:
                                    current_game["title"] = game["title"]
                                    current_game["initialprice"] = game["initialprice"]
                            watchlist.append(current_game)
            
        json_response = {"result": is_valid, "watchlist": watchlist}
        return json_response
