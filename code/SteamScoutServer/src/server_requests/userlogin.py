'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os
from dataupdates.fileaccess import FileAccess
from server_requests.watchlistgamefetcher import WatchlistGameFetcher

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
        service = _UserLoginService()
        filename = 'user_table_test.json' if test_mode else 'user_table.json'
        return service.attempt_login(self.user_name, self.password, filename)
        
    
class _UserLoginService(object):
    '''
    Used for production purposes. When the database is available, 
    then this class is used to perform the UserLogin service.
    '''
    
    def attempt_login(self, user_name, password, filename):
        '''
        Fetches the watchlist data for the given username and password
        pair. The data is retrieved from the data stored in the database.
        
        @param user_name : string - The username for the login.
        @param password : string - The password for the login.
        
        @return: The json response object.
        '''
        is_valid = False
        user_data = FileAccess.read_user_table(lambda user_json: self._read_user_table(user_json), filename)
        if user_name in user_data and user_data[user_name]['password'] == password:
            is_valid = True
                    
        watchlist_game_fetch = WatchlistGameFetcher(user_name)
        results = watchlist_game_fetch.process_service(test_mode=(filename == 'user_table_test.json'))
            
        return {"result": is_valid, "watchlist": results['games_on_watchlist'] if is_valid else []}
    
    def _read_user_table(self, file):
        user_data = json.load(file)
        return user_data;
