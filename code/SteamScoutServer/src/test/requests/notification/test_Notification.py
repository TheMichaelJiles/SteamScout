'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from requests.notification import Notification

class TestNotification(unittest.TestCase):


    def test_getsnotifications_diamondminer74(self):
        service = Notification('diamondminer74')
        results = service.process_service(test_mode = True)
        self.assertEqual('Darklands', results['notifications'][0]['title'])
        self.assertEqual('X4: Foundations', results['notifications'][1]['title'])
        
    def test_getsnotifications_elitegamer(self):
        service = Notification('xxx_elitegamer_xxx')
        results = service.process_service(test_mode = True)
        self.assertEqual(0, len(results['notifications']))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()