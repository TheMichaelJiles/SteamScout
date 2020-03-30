'''
Created on Mar 24, 2020

@author: Michael Jiles
'''
import unittest
from server_requests.watchlistmodification import WatchlistModification
from server_requests.watchlistgamefetcher import WatchlistGameFetcher


class Test(unittest.TestCase):

    def test_modify_all_criteria(self):
        username = 'diamondminer74'
        service = WatchlistGameFetcher(username)
        results = service.process_service(test_mode=True)
        games = results['games_on_watchlist']
        game = games[0];
        steam_id = game['steamid'];
        initial_targetprice_criteria = game['targetprice_criteria']
        initial_onsale_selected = game['onsale_selected']
        initial_targetprice_selected = game['targetprice_selected']
        
        new_targetprice_criteria = initial_targetprice_criteria + 5
        new_onsale_selected = not initial_onsale_selected
        new_targetprice_selected = not initial_targetprice_selected
        
        
        modification_service = WatchlistModification(username, steam_id, new_onsale_selected, new_targetprice_criteria, new_targetprice_selected)
        results = modification_service.process_service(test_mode = True)
        self.assertTrue(results['result'])
        
        games_on_watchlist = results['games_on_watchlist']
        diamondminer74_2 = games_on_watchlist[0]
        self.assertEqual(new_targetprice_criteria, diamondminer74_2['targetprice_criteria'])
        self.assertEqual(new_onsale_selected, diamondminer74_2['onsale_selected'])
        self.assertEqual(new_targetprice_selected, diamondminer74_2['targetprice_selected'])
        
        reset_modification_service = WatchlistModification(username, steam_id, initial_onsale_selected, initial_targetprice_criteria, initial_targetprice_selected)
        reset_modification_service.process_service(test_mode = True)
    
    def test_modify_unknown_game(self):
        username = 'diamondminer74'
        service = WatchlistGameFetcher(username)
        results = service.process_service(test_mode=True)
        games = results['games_on_watchlist']
        game = games[0];
        steam_id = 'not a real game name, for sure';
        initial_targetprice_criteria = game['targetprice_criteria']
        initial_onsale_selected = game['onsale_selected']
        initial_targetprice_selected = game['targetprice_selected']
        
        new_targetprice_criteria = initial_targetprice_criteria + 5
        new_onsale_selected = not initial_onsale_selected
        new_targetprice_selected = not initial_targetprice_selected
        
        
        modification_service = WatchlistModification(username, steam_id, new_onsale_selected, new_targetprice_criteria, new_targetprice_selected)
        results = modification_service.process_service(test_mode = True)
        self.assertFalse(results['result'])
        
if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()