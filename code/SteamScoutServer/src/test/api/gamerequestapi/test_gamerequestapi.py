'''
Created on Mar 26, 2020

@author: Nathan Lightholder
'''
import unittest
from mock import patch
from api.gamerequestapi import _GameRequestService
from api.gamerequestapi import GameRequestAPI
from api.gamerequestapi import _FakeGameRequestService
from api.apihandler import APIHandler


class Test(unittest.TestCase):


    def setUp(self):
        pass


    def tearDown(self):
        pass

    @patch.object(_FakeGameRequestService, 'attempt_get_info')
    def test_fake_get_info(self, mock):
        updater = GameRequestAPI(None)
        updater.get_info(True)
        
        self.assertTrue(mock.called)
        
    @patch.object(_GameRequestService, 'attempt_get_info')
    def test_get_info(self, mock):
        updater = GameRequestAPI(None)
        updater.get_info(False)
        
        self.assertTrue(mock.called)
        
    def test_fake_attempt_get_info_with_3(self):
        service = _FakeGameRequestService()
        result = service.attempt_get_info(3);
        info = {'steamid': 3, 'initialprice': 39.99, 'actualprice': 39.99, 'onsale': False}
        
        self.assertEqual(result, info)
        
    def test_fake_attempt_get_info_with_4(self):
        service = _FakeGameRequestService()
        result = service.attempt_get_info(4);
        info = {'steamid': 4, 'initialprice': 39.99, 'actualprice': 29.99, 'onsale': True}
        
        self.assertEqual(result, info)
        
    def test_fake_attempt_get_info_with_nonexisting_entry(self):
        service = _FakeGameRequestService()
        result = service.attempt_get_info(000000);
        
        self.assertEqual(result, None)
        
    '''def test_attempt_get_info_with_nonexisting_entry(self):
        handler = APIHandler(0, 1)
        service = _GameRequestService(handler)
        result = service.attempt_get_info(00000);
        
        self.assertEqual(result, None)'''
        

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.test_get_info']
    unittest.main()