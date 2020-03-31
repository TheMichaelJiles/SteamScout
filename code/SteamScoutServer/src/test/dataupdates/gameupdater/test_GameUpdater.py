'''
Created on Mar 29, 2020

@author: Nathan Lightholder
'''
import unittest
import json
from mock import patch
from dataupdates.gameupdater import GameUpdater
from api.gamepull import GamePull


class Test(unittest.TestCase):

    @patch.object(json, 'dump')
    def test_game_updater_test(self, mock):
        updater = GameUpdater()
        result = updater.fill_game_table(True)
        
        self.assertTrue(result)
    
    '''@patch.object(GamePull, 'pull_games')    
    def test_game_updater_fails(self, mock):
        updater = GameUpdater()
        mock.return_value = '0'
        result = updater.fill_game_table(True)
        
        self.assertFalse(result)'''

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()