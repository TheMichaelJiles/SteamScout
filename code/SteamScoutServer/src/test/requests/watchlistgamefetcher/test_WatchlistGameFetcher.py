'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from requests.watchlistgamefetcher import WatchlistGameFetcher

class TestWatchlistGameFetcher(unittest.TestCase):

    def test_gets_games_for_diamondminer74(self):
        service = WatchlistGameFetcher('diamondminer74')
        results = service.process_service(test_mode=True)
        games = results['games_on_watchlist']
        self.assertEqual(2, len(games))
        
        darklands = games[0]
        self.assertEqual(1, darklands['steamid'])
        self.assertEqual('Darklands', darklands['title'])
        self.assertEqual(60.0, darklands['initialprice'])
        self.assertEqual(25.0, darklands['actualprice'])
        self.assertEqual(True, darklands['onsale'])
        
        foundations = games[1]
        self.assertEqual(2, foundations['steamid'])
        self.assertEqual('X4: Foundations', foundations['title'])
        self.assertEqual(15.0, foundations['initialprice'])
        self.assertEqual(15.0, foundations['actualprice'])
        self.assertEqual(False, foundations['onsale'])
        
    def test_gets_games_for_unknownuser(self):
        service = WatchlistGameFetcher('lwskjfgoiwej')
        results = service.process_service(test_mode=True)
        games = results['games_on_watchlist']
        self.assertEqual(0, len(games))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()