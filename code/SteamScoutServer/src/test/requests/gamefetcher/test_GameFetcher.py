'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from server_requests.gamefetcher import GameFetcher

class TestGameFetcher(unittest.TestCase):

    def test_fetches_all_games(self):
        service = GameFetcher()
        results = service.process_service(test_mode = True)
        ids = map(lambda game: game['steamid'], results['games'])
        self.assertTrue(1 in ids)
        self.assertTrue(2 in ids)
        self.assertTrue(3 in ids)
        self.assertTrue(4 in ids)
        self.assertTrue(5 in ids)
        self.assertTrue(6 in ids)
        self.assertTrue(23942 in ids)
        self.assertTrue(19510 in ids)
        self.assertTrue(15498 in ids)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()