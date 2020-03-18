'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from requests.gamefetcher import GameFetcher

class TestGameFetcher(unittest.TestCase):

    def test_fetches_all_games(self):
        service = GameFetcher()
        results = service.process_service(test_mode = True)
        self.assertEqual('Darklands', results['games'][0]['title'])
        self.assertEqual('X4: Foundations', results['games'][1]['title'])
        self.assertEqual('Streets of Rogue', results['games'][2]['title'])
        self.assertEqual('Holdfast: Nations At War', results['games'][3]['title'])
        self.assertEqual('GreedFall', results['games'][4]['title'])
        self.assertEqual('Remnant: From the Ashes', results['games'][5]['title'])
        self.assertEqual('Jurassic Park', results['games'][6]['title'])
        self.assertEqual('Home Alone: Revenge', results['games'][7]['title'])
        self.assertEqual('Mr. Burger: Taste the Grease', results['games'][8]['title'])

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()