'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os
from dataupdates.fileaccess import FileAccess

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
        service = _AccountCreatorService()
        filename = 'user_table_test.json' if test_mode else 'user_table.json'
        return service.attempt_create_account(self.user_name, self.password, self.email, filename)
        
class _AccountCreatorService(object):
    '''
    Used for production purposes. When the database is available, 
    then this class is used to perform the AccountCreation service.
    '''
    
    def attempt_create_account(self, user_name, password, email, filename):
        '''
        Attempts to create an account for the user. The data is retrieved 
        from the database.
        
        @param user_name : string - The username for the login.
        @param password : string - The password for the login.
        @param email : string - The user's email.
        
        @return: The json response object.
        '''
        user_data = FileAccess.read_user_table(lambda file: self._read_user_table(file), filename)
        username_already_exists = user_name in user_data
        username_doesnt_already_exist = not username_already_exists
        if username_doesnt_already_exist:
            user_data[user_name] = {'password': password,
                                    'email': email,
                                    'steamid': 0}
        FileAccess.write_user_table(lambda user_data, file: self._write_user_table(user_data, file), filename, user_data)
        
        details = 'Creation Successful.' if username_doesnt_already_exist else 'Creation Unsuccessful: Username Already Taken.'
        json_response = {"result": username_doesnt_already_exist, "details": details}
        return json_response
    
    def _read_user_table(self, file):
        user_data = json.load(file)
        return user_data;
    
    def _write_user_table(self, user_data, file):
        return json.dump(user_data, file)
            