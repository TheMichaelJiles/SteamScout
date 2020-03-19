'''
Created on Mar 19, 2020

@author: luke
'''
import unittest
from requests.accountremoval import AccountRemoval
from requests.accountcreator import AccountCreator

class TestAccountRemoval(unittest.TestCase):


    def test_successfully_removes_account(self):
        removal_service = AccountRemoval('diamondminer74', 'ilovemining')
        results = removal_service.process_service(test_mode=True)
        self.assertTrue(results['result'])

        creation_service = AccountCreator('diamondminer74', 'ilovemining', 'bigpick@example.com')
        results = creation_service.process_service(test_mode=True)
        self.assertTrue(results['result'])
        
    def test_invalidpassword_doesnot_remove_account(self):
        removal_service = AccountRemoval('diamondminer74', 'wefghrwg')
        results = removal_service.process_service(test_mode=True)
        self.assertFalse(results['result'])
        
    def test_unknown_username(self):
        removal_service = AccountRemoval('awhgwagw', 'ilovemining')
        results = removal_service.process_service(test_mode=True)
        self.assertFalse(results['result'])

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()