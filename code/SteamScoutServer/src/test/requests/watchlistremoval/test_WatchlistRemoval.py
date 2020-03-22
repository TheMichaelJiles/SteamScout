'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from server_requests.watchlistaddition import WatchlistAddition
from server_requests.watchlistremoval import WatchlistRemoval

class TestWatchlistRemoval(unittest.TestCase):


    def test_removes_darklands_from_diamondminer74(self):
        removal_service = WatchlistRemoval('diamondminer74', 1)
        removal_results = removal_service.process_service(test_mode=True)
        self.assertEqual(1, len(removal_results['games_on_watchlist']))
        self.assertEqual(2, removal_results['games_on_watchlist'][0]['steamid'])
        
        addition_service = WatchlistAddition('diamondminer74', 1)
        addition_results = addition_service.process_service(test_mode=True)
        self.assertEqual(2, len(addition_results['games_on_watchlist']))
        self.assertEqual(1, addition_results['games_on_watchlist'][1]['steamid'])
        
    def test_remove_game_not_on_watchlist(self):
        removal_service = WatchlistRemoval('diamondminer74', 6)
        removal_results = removal_service.process_service(test_mode=True)
        self.assertEqual(2, len(removal_results['games_on_watchlist']))
        
    def test_remove_nonexisting_game(self):
        removal_service = WatchlistRemoval('diamondminer74', -6)
        removal_results = removal_service.process_service(test_mode=True)
        self.assertEqual(2, len(removal_results['games_on_watchlist']))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()