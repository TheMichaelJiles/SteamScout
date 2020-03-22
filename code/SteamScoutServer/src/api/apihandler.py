'''
Created on Mar 19, 2020

@author: luke
'''

import threading
from requests import get

class APIHandler(object):
    '''
    This class ensures that api call limits are not exceeded.
    '''

    def __init__(self, timer_reset_seconds, limit):
        '''
        Constructs the handler with the specified timer reset and the limit.
        If the api can not be called more than 200 times within 5 minutes, then
        timer_reset_seconds = 5 * 60 = 300 and limit = 200.
        
        @param timer_reset_seconds : the number of seconds before resetting the timer.
        @param limit : The maximum number of calls that can be made before the timer is reset.
        '''
        self.api_calls_made = 0
        self.limit = limit
        self.timer = threading.Timer(timer_reset_seconds, self._reset_api_calls_made)
        self.is_started = False
        
    def make_request(self, url):
        '''
        Attempts to make a request. If the timer has not yet been started, then the timer
        is started. If the number of calls made exceeds the limit, then the was_successful
        property in the returned dictionary will be false. If the call was successful, then
        the was_successful property will be true and the json property will contain the returned
        json dictionary from the request.
        
        @param url : The url for the api request.
        @return the results of the attempted call.
        '''
        if not self.is_started:
            self.timer.start()
            self.is_started = True
        if self.api_calls_made >= self.limit:
            return {'was_successful': False}
        self.api_calls_made += 1
        return {'was_successful': True, 'json': get(url).json()}
        
    def stop_timer(self):
        self.timer.cancel()
        
    def _reset_api_calls_made(self):
        self.api_calls_made = 0