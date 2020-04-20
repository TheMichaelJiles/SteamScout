'''
Created on Apr 19, 2020

@author: Nathaniel Lightholder
'''
import threading

class FileAccess(object):
    '''
    Handles access of the data to prevent files from
    being accessed while already in use
    '''

    file_lock = threading.Lock()
    
    @staticmethod       
    def access_file(service):
        '''
        Executes the passed in function after thread obtains the lock and
        prevents other threads from operating on files at the same time
        
        @precondition none
        @return the return value of the passed in function
        
        @param service The service attempting to access the applications data files
        '''
        with FileAccess.file_lock:
            return service
            
            