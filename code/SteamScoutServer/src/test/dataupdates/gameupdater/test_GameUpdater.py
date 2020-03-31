'''
Created on Mar 29, 2020

@author: Nathan Lightholder
'''
import unittest, os, json
from mock import patch
from dataupdates.gameupdater import GameUpdater
from api.gamepull import GamePull


class TestGameUpdater(unittest.TestCase):


    def test_game_updater_test(self):
        
        updater = GameUpdater()
        result = updater.fill_game_table(True)
        
        self.assertTrue(result)
        
        path = os.path.join(os.path.dirname(__file__), '..', '..', '..', 'test_data', 'game_table_test.json')
        
        keys_to_remove = []
        with open(path, 'r') as jsonfile:
            test_table = json.load(jsonfile)
            for key in test_table:
                if key == '660010' or key == '660130':
                    keys_to_remove.append(key)
            for currkey in keys_to_remove:
                test_table.pop(currkey, None)
        with open(path, 'w') as jsonfile:
            json.dump(test_table, jsonfile)
    
    @patch.object(GamePull, 'pull_games')    
    def test_game_updater_fails(self, mock):
        updater = GameUpdater()
        mock.return_value = '0'
        result = updater.fill_game_table(True)
        
        self.assertFalse(result)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()