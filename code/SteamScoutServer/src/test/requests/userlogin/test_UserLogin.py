'''
Created on Mar 18, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''
import unittest
from requests.userlogin import UserLogin

class TestUserLogin(unittest.TestCase):

    def test_login_diamondminer74(self):
        service = UserLogin('diamondminer74', 'ilovemining')
        result = service.process_service(test_mode = True)
        was_successful = result['result']
        self.assertTrue(was_successful)
        
        watchlist = result['watchlist']
        first_watchlist_steamid = watchlist[0]['steamid']
        second_watchlist_steamid = watchlist[1]['steamid']
        self.assertEqual(first_watchlist_steamid, 1)
        self.assertEqual(second_watchlist_steamid, 2)
        
    
    def test_login_xxx_elitegamer_xxx(self):
        service = UserLogin('xxx_elitegamer_xxx', 'irektu')
        result = service.process_service(test_mode = True)
        was_successful = result['result']
        self.assertTrue(was_successful)
        
        watchlist = result['watchlist']
        first_watchlist_steamid = watchlist[0]['steamid']
        second_watchlist_steamid = watchlist[1]['steamid']
        self.assertEqual(first_watchlist_steamid, 3)
        self.assertEqual(second_watchlist_steamid, 4)
    
    def test_login_X_CorleyStud_X(self):
        service = UserLogin('X_CorleyStud_X', 'betterthanyoder')
        result = service.process_service(test_mode = True)
        was_successful = result['result']
        self.assertTrue(was_successful)
        
        watchlist = result['watchlist']
        first_watchlist_steamid = watchlist[0]['steamid']
        second_watchlist_steamid = watchlist[1]['steamid']
        self.assertEqual(first_watchlist_steamid, 5)
        self.assertEqual(second_watchlist_steamid, 6)
        
    def test_login_unknownuser(self):
        service = UserLogin('bearfoot', 'dolphin')
        result = service.process_service(test_mode = True)
        was_successful = result['result']
        self.assertFalse(was_successful)
        self.assertEqual(0, len(result['watchlist']))
        
    def test_login_knownuser_incorrectpassword(self):
        service = UserLogin('X_CorleyStud_X', 'iforgot')
        result = service.process_service(test_mode = True)
        was_successful = result['result']
        self.assertFalse(was_successful)
        self.assertEqual(0, len(result['watchlist']))


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'TestUserLogin.testName']
    unittest.main()