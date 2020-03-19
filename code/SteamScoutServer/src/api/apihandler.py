'''
Created on Mar 19, 2020

@author: luke
'''

import threading
import requests

class APIHandler(object):
    '''
    '''

    def __init__(self, timer_reset_seconds, limit):
        '''
        '''
        self.api_calls_made = 0
        self.limit = limit
        self.timer = threading.Timer(timer_reset_seconds, self._reset_api_calls_made)
        self.timer.start()
        
    def make_request(self, url):
        if self.api_calls_made >= self.limit:
            return {'was_successful': False}
        self.api_calls_made += 1
        return {'was_successful': True, 'json': requests.get(url).json()}
        
    def _reset_api_calls_made(self):
        self.api_calls_made = 0