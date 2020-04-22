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
    
    '''

    def send_emails(self, test_mode=False):
        '''
        
        '''
        print("Starting to Send E-Mails")
        user_data = FileAccess.read_user_table(lambda f: self._user_data_read(f), 'user_table_test.json' if test_mode else 'user_table.json')
        for username in user_data:
            notification_service = Notification(username)
            notification_data = notification_service.process_service(test_mode)['notifications']
            print(notification_data)
            for data in notification_data:
                formatter = EmailFormatter(data['steamid'], data['actualprice'], data['initialprice'], f'https://store.steampowered.com/app/{data["steamid"]}')
                formatted_email = formatter.format_for_email()
                sender = EmailSender(user_data[username]['email'], formatted_email)
                sender.send_email()
        print("Finished Sending E-Mails")
    
    def _user_data_read(self, jsonfile):
        return json.load(jsonfile)