'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os

class AccountCreator(object):
    '''
    Provides a service that creates a user account. If the 
    username is already taken, then the account is not created.
    '''

    def __init__(self, user_name, password, email):
        '''
        Creates a new AccountCreator object with the specified
        user_name, password, and email.
        
        @param user_name: string - the username for the account.
        @param password: string - the password for the account.
        @param email: string - the email for the account.
        '''
        self.user_name = user_name
        self.password = password
        self.email = email
        
    def process_service(self, test_mode = False):
        '''
        Attempts to create an account for the user. This service returns a 
        json response object that should be sent back to the client.
        It contains information that describes if the account creation was
        successful or not.
        
        @postcondition: if the username is not found in the system, then
                        process_service()["result"] == True and process_service()["details"] == "Creation Successful."
                        
                        if the username is already taken by another user,
                        then process_service()["result"] == False and 
                        process_service()["details"] == "Creation Unsucessful: Username Already Taken."
                            
        @param test_mode: boolean - Determines if the process should be run in test mode.
                            
        @return: the json response to the client
        '''
        service = _FakeAccountCreatorService() if test_mode else _AccountCreatorService()
        return service.attempt_create_account(self.user_name, self.password, self.email)
        
class _FakeAccountCreatorService(object):
    '''
    Used for testing purposes. When the database is not available, 
    or when performing tests, then this class is used to perform the 
    AccountCreation service.
    '''
    
    def attempt_create_account(self, user_name, password, email):
        '''
        Attempts to create an account for the user. The data is retrieved 
        from the json files within the SteamScoutServer/test_data
        directory.
        
        @param user_name : string - The username for the login.
        @param password : string - The password for the login.
        @param email : string - The user's email.
        
        @return: The json response object.
        '''
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table_test.json'), 'r') as jsonfile:
            user_data = json.load(jsonfile)
            username_already_exists = user_name in user_data
            username_doesnt_already_exist = not username_already_exists
        if username_doesnt_already_exist:
            user_data[user_name] = {'password': password,
                                    'email': email,
                                    'steamid': 0}
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table_test.json'), 'w') as jsonfile:
                json.dump(user_data, jsonfile)
        
        details = 'Creation Successful.' if username_doesnt_already_exist else 'Creation Unsuccessful: Username Already Taken.'
        json_response = {"result": username_doesnt_already_exist, "details": details}
        return json_response
        
class _AccountCreatorService(object):
    '''
    Used for production purposes. When the database is available, 
    then this class is used to perform the AccountCreation service.
    '''
    
    def attempt_create_account(self, user_name, password, email):
        '''
        Attempts to create an account for the user. The data is retrieved 
        from the database.
        
        @param user_name : string - The username for the login.
        @param password : string - The password for the login.
        @param email : string - The user's email.
        
        @return: The json response object.
        '''
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table.json'), 'r') as jsonfile:
            user_data = json.load(jsonfile)
            username_already_exists = user_name in user_data
            username_doesnt_already_exist = not username_already_exists
        if username_doesnt_already_exist:
            user_data[user_name] = {'password': password,
                                    'email': email,
                                    'steamid': 0}
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table.json'), 'w') as jsonfile:
                json.dump(user_data, jsonfile)
        
        details = 'Creation Successful.' if username_doesnt_already_exist else 'Creation Unsuccessful: Username Already Taken.'
        json_response = {"result": username_doesnt_already_exist, "details": details}
        return json_response
            