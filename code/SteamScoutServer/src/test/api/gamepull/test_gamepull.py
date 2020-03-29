'''
Created on Mar 29, 2020

@author: Nathan Lightholder
'''
import unittest
from mock import patch
from api.gamepull import GamePull
from api.gamepull import _FakeGamePullService
from api.gamepull import _GamePullService
from api.apihandler import APIHandler


class TestGamePull(unittest.TestCase):


    @patch.object(_GamePullService, 'make_pull')
    def test_get_info(self, mock):
        updater = GamePull()
        updater.pull_games(False)
        
        self.assertTrue(mock.called)
        
    @patch.object(_FakeGamePullService, 'make_pull')
    def test_fake_get_info(self, mock):
        updater = GamePull()
        updater.pull_games(True)
        
        self.assertTrue(mock.called)
        
    def test_fake_game_pull(self):
        service = _FakeGamePullService()
        
        self.assertEqual(service.make_pull(), {'660010': 'test2', '660130': 'test3'})
     
    @patch.object(APIHandler, 'make_request')    
    def test_game_pull_without_json(self, mock):
        handler = APIHandler(0, 1)
        service = _GamePullService(handler)
        mock.return_value = {'json': None}
        result = service.make_pull()
        
        self.assertEqual(result, None)
        
    @patch.object(APIHandler, 'make_request')    
    def test_game_pull_without_applist(self, mock):
        handler = APIHandler(0, 1)
        service = _GamePullService(handler)
        mock.return_value = {'json': {'notAppList': None}}
        result = service.make_pull()
        
        self.assertEqual(result, None)
        
    @patch.object(APIHandler, 'make_request')    
    def test_game_pull_without_apps(self, mock):
        handler = APIHandler(0, 1)
        service = _GamePullService(handler)
        mock.return_value = {'json': {'applist': {'notApps': None}}}
        result = service.make_pull()
        
        self.assertEqual(result, None)
        
    @patch.object(APIHandler, 'make_request')    
    def test_game_pull_without_app(self, mock):
        handler = APIHandler(0, 1)
        service = _GamePullService(handler)
        mock.return_value = {'json': {'applist': {'apps': {'notApp': None}}}}
        result = service.make_pull()
        
        self.assertEqual(result, None)
        
    @patch.object(APIHandler, 'make_request')    
    def test_game_pull_valid_result(self, mock):
        handler = APIHandler(0, 1)
        service = _GamePullService(handler)
        mock.return_value = {'json': {'applist': {'apps': {'app': [{'appid': 101, 'name': 'cows 2'}]}}}}
        result = service.make_pull()
        
        self.assertEqual(result['101'], 'cows 2')
        
    @patch.object(APIHandler, 'make_request')    
    def test_game_pull_multiple_valid_results(self, mock):
        handler = APIHandler(0, 1)
        service = _GamePullService(handler)
        mock.return_value = {'json': {'applist': {'apps': {'app': [
            {'appid': 101, 'name': 'cows 2'}, {'appid': 102, 'name': 'cows 1'}]}}}}
        result = service.make_pull()
        
        self.assertEqual(result['102'], 'cows 1')


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()