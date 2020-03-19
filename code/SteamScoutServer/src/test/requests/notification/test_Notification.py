'''
Created on Mar 18, 2020

@author: luke
'''
import unittest
from requests.notification import Notification

class TestNotification(unittest.TestCase):


    def test_getsnotifications_corleystud(self):
        service = Notification('X_CorleyStud_X')
        results = service.process_service(test_mode = True)
        notification_ids = map(lambda notification: notification['steamid'], results['notifications'])
        self.assertTrue(5 in notification_ids)
        self.assertTrue(6 in notification_ids)
    
    
    def test_getsnotifications_elitegamer(self):
        service = Notification('xxx_elitegamer_xxx')
        results = service.process_service(test_mode = True)
        self.assertEqual(0, len(results['notifications']))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()