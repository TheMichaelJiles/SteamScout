'''
Created on Apr 14, 2020

@author: theke
'''
import smtplib

class EmailSender(object):
    '''
    Class responsible for sending a message to the designated target
    '''


    def __init__(self, email_target, message):
        '''
        Creates an instance of an EmailSender object, storing the email_target and message variables
        @precondition none
        @postcondition self.email_target == email_target, self.message == message
        '''
        self.email_target = email_target
        self.message = message
    
    def send_email(self):
        '''
        Sends the message stored in this class to the target email
        @precondition none
        @postcondition the email is sent to the target
        ''' 
        email_server = smtplib.SMTP(host='smtp.gmail.com', port='587')
        email_server.starttls()
        email_server.login('steamscoutnotify@gmail.com', 'SteamScoutNotify')
        
        self.message['To']=self.email_target
        
        email_server.send_message(self.message)
        print(f'E-Mail Sent to {self.email_target}')
        email_server.quit()
        
        