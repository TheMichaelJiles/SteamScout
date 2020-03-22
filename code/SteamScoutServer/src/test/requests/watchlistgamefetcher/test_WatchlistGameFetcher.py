'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from server_requests.watchlistgamefetcher import WatchlistGameFetcher

class TestWatchlistGameFetcher(unittest.TestCase):

    def test_gets_games_for_diamondminer74(self):
        service = WatchlistGameFetcher('diamondminer74')
        results = service.process_service(test_mode=True)
        games = results['games_on_watchlist']
        self.assertEqual(2, len(games))
        
        
    def test_gets_games_for_unknownuser(self):
        service = WatchlistGameFetcher('lwskjfgoiwej')
        results = service.process_service(test_mode=True)
        games = results['games_on_watchlist']
        self.assertEqual(0, len(games))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()