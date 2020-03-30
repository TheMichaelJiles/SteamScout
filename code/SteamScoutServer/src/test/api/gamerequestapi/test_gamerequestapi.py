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


class TestGameRequestAPI(unittest.TestCase):

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
        
    '''@patch.object(APIHandler, 'make_request')
    def test_attempt_get_info_with_JSON_request_unsuccessful(self, mock):
        handler = APIHandler(0, 1)
        service = _GameRequestService(handler)
        mock.return_value = {'was_successful': False}
        result = service.attempt_get_info(00000);
        
        self.assertEqual(result, None)'''
    
    @patch.object(APIHandler, 'make_request')   
    def test_attempt_get_info_with_JSON_is_none(self, mock):
        handler = APIHandler(0, 1)
        service = _GameRequestService(handler)
        mock.return_value = {'was_successful': True, 'json': None}
        result = service.attempt_get_info(00000);
        handler.stop_timer()
        
        self.assertEqual(result, None)
        
    @patch.object(APIHandler, 'make_request')   
    def test_attempt_get_info_with_unsuccessful(self, mock):
        handler = APIHandler(0, 0)
        service = _GameRequestService(handler)
        mock.return_value = {'was_successful': True, 'json': {'4': {'success': True, 'data': {'price_overview': {'final': 3999, 'initial': 2999, 'discount_percent': 5}}}}}
        result = service.attempt_get_info(00000);
        handler.stop_timer()
        
        self.assertEqual(result, None)
    
    @patch.object(APIHandler, 'make_request')    
    def test_attempt_get_info_without_data(self, mock):
        handler = APIHandler(0, 1)
        service = _GameRequestService(handler)
        mock.return_value = {'was_successful': True, 'json': 
                            {'success': True, 'steamid': 4, 'initialprice': 39.99, 'actualprice': 29.99, 'onsale': True}}
        result = service.attempt_get_info(00000);
        handler.stop_timer()
        
        self.assertEqual(result, None)
        
    @patch.object(APIHandler, 'make_request')    
    def test_attempt_get_info_without_price_overview(self, mock):
        handler = APIHandler(0, 1)
        service = _GameRequestService(handler)
        mock.return_value = {'was_successful': True, 'json': 
                            {'success': True, 'data': 
                            {'steamid': 4, 'initialprice': 39.99, 'actualprice': 29.99, 'onsale': True}}}
        result = service.attempt_get_info(00000);
        handler.stop_timer()
        
        self.assertEqual(result, None)
        
    @patch.object(APIHandler, 'make_request')    
    def test_attempt_get_info_with_valid_data(self, mock):
        handler = APIHandler(0, 1)
        service = _GameRequestService(handler)
        mock.return_value = {'was_successful': True, 'json': 
                            {'success': True, 'data': 
                            {'steamid': 4, 'price_overview': 
                            {'initial': 3999, 'final': 1999, 'discount_percent': 50}}}}
        result = service.attempt_get_info(4);
        handler.stop_timer()
        
        self.assertEqual(result, {'steamid': 4,
                'initialprice': 3999 / 100,
                'actualprice': 1999 / 100,
                'onsale': 50 > 0})
        

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.test_get_info']
    unittest.main()