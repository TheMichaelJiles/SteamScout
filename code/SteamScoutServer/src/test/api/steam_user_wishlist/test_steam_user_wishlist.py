'''
Created on Mar 29, 2020

@author: Nathan Lightholder
'''
import unittest
from mock import patch
from api.steam_user_wishlist import WishlistRequestAPI
from api.steam_user_wishlist import _FakeWishlistRequestService
from api.steam_user_wishlist import _WishlistRequestService
from api.apihandler import APIHandler


class TestWishlistRequestAPI(unittest.TestCase):

    @patch.object(_FakeWishlistRequestService, 'make_request')
    def test_fake_fetch_wishlist(self, mock):
        fetcher = WishlistRequestAPI(None)
        fetcher.fetch_wishlist(True)
        
        self.assertTrue(mock.called)
        
    @patch.object(_WishlistRequestService, 'make_request')
    def test_fetch_wishlist(self, mock):
        fetcher = WishlistRequestAPI(None)
        fetcher.fetch_wishlist(False)
        
        self.assertTrue(mock.called)
        
    def test_fake_make_request(self):
        service = _FakeWishlistRequestService()
        result = service.make_request(None)
        
        self.assertEqual(result, [{"steamid": 23942, "title": "Jurassic Park"},
                                  {"steamid": 19510, "title": "Home Alone: Revenge"},
                                  {"steamid": 15498, "title": "Mr. Burger: Taste the Grease"}])
    
    @patch.object(APIHandler, 'make_request')    
    def test_make_request(self, mock):
        handler = APIHandler(0, 1)
        service = _WishlistRequestService(handler)
        service.make_request(None)
        
        self.assertTrue(mock.called)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()