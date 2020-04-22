'''
Created on Apr 16, 2020

@author: theke
'''
import unittest
from emailnotification.email_sender import EmailSender
from email.mime.text import MIMEText

class FakeEmailServer(object):
    def __init__(self, host, port):
        self.host = host
        self.port = port
    def starttls(self):
        return
    def login(self, email, password):
        return
    def send_message(self, message):
        return message
    def quit(self):
        return
    
class Test(unittest.TestCase):
    
    def test_successful_send_email(self):
        text = MIMEText("Testing here", 'plain')
        email_target = 'steamscoutnotify@gmail.com'
        text['From'] = email_target
        sender = EmailSender(email_target, text)
        result = sender.send_email(FakeEmailServer(host='smtp.gmail.com', port='587'))
        text['To']= email_target
        self.assertEquals(result, text)


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.test_send_email']
    unittest.main()