'''
Created on Mar 21, 2020

@author: luke
'''
import unittest
from api.apihandler import APIHandler

class TestAPIHandler(unittest.TestCase):


    def test_does_nothing_if_limit_met(self):
        handler = APIHandler(2934932, 0)
        result = handler.make_request(None)
        self.assertFalse(result['was_successful'])
        handler.stop_timer()

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()