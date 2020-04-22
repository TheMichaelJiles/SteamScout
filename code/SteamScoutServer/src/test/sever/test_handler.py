'''
Created on Apr 21, 2020

@author: Nlight160
'''
import unittest
from mock import patch
from handler import ClientHandler
from server_requests.servicefactory import ServiceFactory
from api.apihandler import APIHandler

class TestHandler(unittest.TestCase):

    
    @patch.object(ServiceFactory, 'create_service')
    def testProcessServiceIsCalled(self, mock):
        handler = ClientHandler('test_json')
        api = APIHandler(0, 0)
        handler.process_request(api, True)
        
        self.assertTrue(mock.called)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testProcessServiceIsCalled']
    unittest.main()