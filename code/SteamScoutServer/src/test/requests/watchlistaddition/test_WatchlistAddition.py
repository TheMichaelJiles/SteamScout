'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from server_requests.watchlistaddition import WatchlistAddition
from server_requests.watchlistremoval import WatchlistRemoval

class TestWatchlistAddition(unittest.TestCase):

    def test_add_mrburger_to_diamondminer74(self):
        addition_service = WatchlistAddition('diamondminer74', 15498)
        results = addition_service.process_service(test_mode=True)
        self.assertTrue(results['result'])
        
        games_on_watchlist = results['games_on_watchlist']
        self.assertEqual(3, len(games_on_watchlist))
        
        mrburger = games_on_watchlist[2]
        self.assertEqual(15498, mrburger['steamid'])
        self.assertEqual('Mr. Burger: Taste the Grease', mrburger['title'])
        self.assertEqual(0.0, mrburger['initialprice'])
        self.assertEqual(0.0, mrburger['actualprice'])
        self.assertEqual(False, mrburger['onsale'])
        
        removal_service = WatchlistRemoval('diamondminer74', 15498)
        removal_results = removal_service.process_service(test_mode=True)
        self.assertEqual(2, len(removal_results['games_on_watchlist']))
        
    def test_add_unknown_game(self):
        addition_service = WatchlistAddition('diamondminer74', -234)
        results = addition_service.process_service(test_mode=True)
        self.assertFalse(results['result'])
        self.assertEqual(2, len(results['games_on_watchlist']))
        
    def test_add_game_already_on_watchlist(self):
        addition_service = WatchlistAddition('diamondminer74', 1)
        results = addition_service.process_service(test_mode=True)
        self.assertFalse(results['result'])
        self.assertEqual(2, len(results['games_on_watchlist']))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()