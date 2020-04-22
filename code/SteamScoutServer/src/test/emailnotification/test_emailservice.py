'''
Created on Apr 21, 2020

@author: Nlight160
'''
import unittest
from mock import patch
from emailnotification.email_sender import EmailSender
from emailnotification.email_service import EmailNotificationService


class Test(unittest.TestCase):

    @patch.object(EmailSender, 'send_email')
    def testEmailsAreSent(self, mock):
        service = EmailNotificationService()
        service.send_emails(True)
        
        self.assertTrue(mock.called)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testEmailsAreSent']
    unittest.main()