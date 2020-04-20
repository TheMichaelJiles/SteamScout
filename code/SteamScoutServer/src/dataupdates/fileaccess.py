'''
Created on Apr 19, 2020

@author: Nathaniel Lightholder
'''
import threading
import os

class FileAccess(object):
    '''
    Handles access of the data to prevent files from
    being accessed while already in use
    '''

    _game_lock = threading.Lock()
    _watchlist_lock = threading.Lock()
    _user_lock = threading.Lock()
    
    @staticmethod    
    def read_game_table(function, filename):
        '''
        Executes the passed in function after thread obtains the lock and
        prevents other threads from reading or writing simultaneously to the game table
        
        @precondition none
        @return the return value of the passed in function
        
        @param function The service attempting to access the applications data files
        @param filename The filename of the game table
        '''
        with FileAccess._game_lock:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'r') as file:
                return function(file)
    
    @staticmethod            
    def read_user_table(function, filename):
        with FileAccess._user_lock:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'r') as file:
                return function(file)
            
    @staticmethod            
    def write_user_table(function, filename, user_data):
        with FileAccess._user_lock:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'w') as file:
                return function(user_data, file)
            
            
    @staticmethod            
    def read_watchlist_table(function, filename):
        with FileAccess._watchlist_lock:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'r') as file:
                return function(file)
            
    @staticmethod            
    def write_watchlist_table(function, filename, watchlist_data):
        with FileAccess._watchlist_lock:
            with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', filename), 'w') as file:
                return function(watchlist_data, file)
                
    
        
        
        
        
        
            
            