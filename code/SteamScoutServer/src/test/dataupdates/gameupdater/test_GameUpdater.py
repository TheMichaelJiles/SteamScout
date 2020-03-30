'''
Created on Mar 29, 2020

@author: Nlight160
'''
import unittest
from mock import patch
from dataupdates.gameupdater import GameUpdater
from api.gamepull import GamePull


class Test(unittest.TestCase):


    def test_game_updater_test(self):
        updater = GameUpdater()
        result = updater.fill_game_table(True)
        
        self.assertTrue(result)
    
    @patch.object(GamePull, 'pull_games')    
    def test_game_updater_fails(self, mock):
        updater = GameUpdater()
        mock.return_value = '0'
        result = updater.fill_game_table(True)
        
        self.assertFalse(result)
        
    def test_game_updater(self):
        updater = GameUpdater()
        result = updater.fill_game_table(False)
        
        self.assertTrue(result)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()