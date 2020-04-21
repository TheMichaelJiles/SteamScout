'''
Created on Mar 19, 2020

@author: luke
'''

import json
import os
from dataupdates.fileaccess import FileAccess

class AccountRemoval(object):
    '''
    The account removal service takes a username and a password.
    If the username exists and the password is correct, then the 
    account is removed.
    '''

    def __init__(self, username, password):
        '''
        Constructs the AccountRemoval object with the specified username and
        password data.
        
        @param username : the desired username of the account to remove
        @param password : the password of the account to remove
        '''
        self.username = username
        self.password = password
        
    def process_service(self, test_mode = False):
        '''
        Processes the account removal service. If test_mode is set to true, then the operations
        will be performed on the json files that end in _test.json within the test_data directory.
        
        @param test_mode : whether or not to run the service in test mode
        @return the resulting dictionary. {'result': True/False} If the result is true, then the account
        was removed. If the result is false, then the account was not removed.
        '''
        service = _AccountRemovalService()
        filename = 'user_table_test.json' if test_mode else 'user_table.json'
        return service.remove_account(self.username, self.password, filename)
        
class _AccountRemovalService(object):
    '''
    The actual account removal service. This services operates on the json filles within the test_data directory
    that do not end in _test.json.
    '''
    
    def remove_account(self, username, password, filename):
        '''
        Removes the account with the specified username and password from the system.
        If the username is not found or the password is incorrect for the given username, then the
        account is not removed.
        
        @param username : the desired username of the account to remove
        @param password : the password of the account to remove
        
        @return the resulting dictionary. {'result': True/False} If the result is true, then the account
        was removed. If the result is false, then the account was not removed.
        '''
        can_remove = False
        user_data = FileAccess.read_user_table(lambda jsonfile: self._read_user_table(jsonfile), filename)
        if username in user_data and user_data[username]['password'] == password:
            can_remove = True
        
        if can_remove:
            user_data.pop(username, None)
            FileAccess.write_user_table(lambda user_data, jsonfile: self._write_user_table(user_data, jsonfile), filename, user_data)
                
        return {'result': can_remove}
    
    def _read_user_table(self, file):
        user_data = json.load(file)
        return user_data;
    
    def _write_user_table(self, user_data, file):
        return json.dump(user_data, file)
        