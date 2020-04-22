'''
Created on Apr 21, 2020

@author: Luke Whaley
'''

import json
from emailnotification.email_formatter import EmailFormatter
from emailnotification.email_sender import EmailSender
from server_requests.notification import Notification
from dataupdates.fileaccess import FileAccess

class EmailNotificationService(object):
    '''
    This service uses the regular notification service to get the data
    for the email notifications.
    '''

    def send_emails(self, test_mode=False):
        '''
        Sends emails for all notifications that meet watchlist criteria.
        
        @param test_mode : whether or not to run the service in test mode.
        '''
        print("Starting to Send E-Mails")
        user_data = FileAccess.read_user_table(lambda f: self._user_data_read(f), 'user_table_test.json' if test_mode else 'user_table.json')
        for username in user_data:
            notification_service = Notification(username)
            notification_data = notification_service.process_service(test_mode)['notifications']
            print(notification_data)
            for data in notification_data:
                print(data)
                formatter = EmailFormatter(data['steamid'], data['actualprice'], data['initialprice'], test_mode)
                formatted_email = formatter.format_for_email()
                sender = EmailSender(user_data[username]['email'], formatted_email)
                sender.send_email()
        print("Finished Sending E-Mails")
    
    def _user_data_read(self, jsonfile):
        return json.load(jsonfile)