'''
Created on Apr 21, 2020

@author: Luke Whaley
'''
import unittest
from server_requests.servicefactory import ServiceFactory
from server_requests.accountcreator import AccountCreator
from server_requests.userlogin import UserLogin
from server_requests.gamefetcher import GameFetcher
from server_requests.watchlistgamefetcher import WatchlistGameFetcher
from server_requests.notification import Notification
from server_requests.steamwishlistlink import SteamWishlistLink
from server_requests.watchlistaddition import WatchlistAddition
from server_requests.watchlistmodification import WatchlistModification
from server_requests.watchlistremoval import WatchlistRemoval

class TestServiceFactory(unittest.TestCase):


    def testMakesAccountCreator(self):
        data = {'type': 'create_account', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, AccountCreator))
    
    def testMakesUserLogin(self):
        data = {'type': 'authenticate', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, UserLogin))
    
    def testMakesGameFetcher(self):
        data = {'type': 'fetch_games', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, GameFetcher))
    
    def testMakesWatchlistGameFetcher(self):
        data = {'type': 'fetch_watchlist', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, WatchlistGameFetcher))
    
    def testMakesNotification(self):
        data = {'type': 'check_notifications', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, Notification))
    
    def testMakesSteamWishlistLink(self):
        data = {'type': 'link_steam', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, SteamWishlistLink))
    
    def testMakesWatchlistAddition(self):
        data = {'type': 'watchlist_addition', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, WatchlistAddition))
    
    def testMakesWatchlistModification(self):
        data = {'type': 'watchlist_modification', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, WatchlistModification))
    
    def testMakesWatchlistRemoval(self):
        data = {'type': 'watchlist_removal', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        service = ServiceFactory.create_service(data, None)
        self.assertTrue(isinstance(service, WatchlistRemoval))
    
    def testRaisesTypeErrorOnInvalidType(self):
        data = {'type': 'awgwaegfaewf', 'data': {'user': {'username': 'test-username', 'password': 'test-password', 'email': 'test@example.com', 'steamid': 1}, 'steamid': 2, 'game': {'steamid': 3, 'onsaleselected': True, 'targetprice': 5.0, 'targetpriceselected': True}}}
        with self.assertRaises(TypeError):
            ServiceFactory.create_service(data, None)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()