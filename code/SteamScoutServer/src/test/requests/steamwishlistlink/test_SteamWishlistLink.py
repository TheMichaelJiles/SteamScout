'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from requests.steamwishlistlink import SteamWishlistLink

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
        
    def test_linksnonemptywatchlist_diamondminer74(self):
        service = SteamWishlistLink('diamondminer74', 0, False, False)
        result = service.process_service(test_mode = True)
        self.assertTrue(result['result'])
        self.assertEqual(1, result['watchlist'][0]['steamid'])
        self.assertEqual(2, result['watchlist'][1]['steamid'])
        self.assertEqual(23942, result['watchlist'][2]['steamid'])
        self.assertEqual(19510, result['watchlist'][3]['steamid'])
        self.assertEqual(15498, result['watchlist'][4]['steamid'])
        self.assertEqual('Darklands', result['watchlist'][0]['title'])
        self.assertEqual('X4: Foundations', result['watchlist'][1]['title'])
        self.assertEqual('Jurassic Park', result['watchlist'][2]['title'])
        self.assertEqual('Home Alone: Revenge', result['watchlist'][3]['title'])
        self.assertEqual('Mr. Burger: Taste the Grease', result['watchlist'][4]['title'])

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()