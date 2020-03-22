'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from server_requests.accountcreator import AccountCreator
from server_requests.accountremoval import AccountRemoval

class TestAccountCreator(unittest.TestCase):


    def test_successfully_createaccount(self):
        service = AccountCreator('mrbimbo', 'password', 'email@example.com')
        results = service.process_service(test_mode = True)
        self.assertTrue(results['result'])
        self.assertEqual('Creation Successful.', results['details'])
        
        removal_service = AccountRemoval('mrbimbo', 'password')
        results = removal_service.process_service(test_mode=True)
        self.assertTrue(results['result'])
        
    def test_unsuccessfully_createaccount(self):
        service = AccountCreator('diamondminer74', 'password', 'email@example.com')
        results = service.process_service(test_mode = True)
        self.assertFalse(results['result'])
        self.assertEqual('Creation Unsuccessful: Username Already Taken.', results['details'])

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()