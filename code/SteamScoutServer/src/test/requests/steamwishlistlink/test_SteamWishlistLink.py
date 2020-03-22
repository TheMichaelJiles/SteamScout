'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from server_requests.steamwishlistlink import SteamWishlistLink
from server_requests.watchlistremoval import WatchlistRemoval
from api.steam_user_wishlist import WishlistRequestAPI

class TestSteamWishlistLink(unittest.TestCase):

    def test_linksemptywatchlist_turkeybob(self):
        service = SteamWishlistLink('turkeybob', 0, False, False)
        result = service.process_service(test_mode = True)
        self.assertTrue(result['result'])
        
        self.assertEqual(23942, result['watchlist'][0]['steamid'])
        self.assertEqual(19510, result['watchlist'][1]['steamid'])
        self.assertEqual(15498, result['watchlist'][2]['steamid'])
        self.assertEqual('Jurassic Park', result['watchlist'][0]['title'])
        self.assertEqual('Home Alone: Revenge', result['watchlist'][1]['title'])
        self.assertEqual('Mr. Burger: Taste the Grease', result['watchlist'][2]['title'])
        
        wishlist_service = WishlistRequestAPI(0)
        wishlist_games = wishlist_service.fetch_wishlist(test_mode = True)
          
        for game in wishlist_games:
            removal_service = WatchlistRemoval('turkeybob', game['steamid'])
            removal_service.process_service(test_mode=True)
        
    

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()