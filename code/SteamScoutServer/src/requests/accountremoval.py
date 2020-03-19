'''
Created on Mar 19, 2020

@author: luke
'''

import json
import os

class AccountRemoval(object):
    '''
    '''


    def __init__(self, username, password):
        '''
        '''
        self.username = username
        self.password = password
        
    def process_service(self, test_mode = False):
        '''
        '''
        service = _FakeAccountRemovalService() if test_mode else _AccountRemovalService()
        return service.remove_account(self.username, self.password)
        
class _FakeAccountRemovalService(object):
    '''
    '''
     
    def remove_account(self, username, password):
        '''
        '''
        can_remove = False
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table_test.json'), 'r') as jsonfile:
            user_data = json.load(jsonfile)
            if username in user_data and user_data[username]['password'] == password:
                can_remove = True
        
        if can_remove:
            user_data.pop(username, None)
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table_test.json'), 'w') as jsonfile:
                json.dump(user_data, jsonfile)
                
        return {'result': can_remove}
        
class _AccountRemovalService(object):
    '''
    '''
    
    def remove_account(self, username, password):
        '''
        '''
        can_remove = False
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table.json'), 'r') as jsonfile:
            user_data = json.load(jsonfile)
            if user_data[username]['password'] == password:
                can_remove = True
        
        if can_remove:
            user_data.pop(username, None)
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'user_table.json'), 'w') as jsonfile:
                json.dump(user_data, jsonfile)
                
        return {'result': can_remove}
        