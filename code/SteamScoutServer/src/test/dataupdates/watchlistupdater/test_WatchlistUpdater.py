'''
Created on Mar 21, 2020

@author: luke
'''
import json
import os
import unittest
from dataupdates.watchlistupdater import WatchlistUpdater

class TestWatchlistUpdater(unittest.TestCase):


    def test_updates_games(self):
        updater = WatchlistUpdater()
        updater.perform_updates(test_mode=True)
        
        game_filename = 'game_table_test.json'
        with open(os.path.join(os.path.dirname(__file__), '..', '..', '..', 'test_data', game_filename), 'r') as jsonfile:
            game_data = json.load(jsonfile)
            
            self.assertEqual(39.99, game_data['3']['initialprice'])
            self.assertEqual(39.99, game_data['3']['actualprice'])
            self.assertFalse(game_data['3']['onsale'])
            
            self.assertEqual(39.99, game_data['4']['initialprice'])
            self.assertEqual(29.99, game_data['4']['actualprice'])
            self.assertTrue(game_data['4']['onsale'])
            
        self._reset_games()

    def _reset_games(self):
        game_filename = 'game_table_test.json'
        with open(os.path.join(os.path.dirname(__file__), '..', '..', '..', 'test_data', game_filename), 'r') as jsonfile:
            game_data = json.load(jsonfile)              
            game_data['3']['initialprice'] = 0.0
            game_data['3']['actualprice'] = 0.0
            game_data['3']['onsale'] = False
            game_data['4']['initialprice'] = 0.0
            game_data['4']['actualprice'] = 0.0
            game_data['4']['onsale'] = False
                
        with open(os.path.join(os.path.dirname(__file__), '..', '..', '..', 'test_data', game_filename), 'w') as jsonfile:
            json.dump(game_data, jsonfile)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()