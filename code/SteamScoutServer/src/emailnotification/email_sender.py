'''
Created on Apr 14, 2020

@author: theke
'''
import smtplib

class EmailSender(object):
    '''
    Class responsible for sending a message to the designated target
    '''
    EMAIL_SERVER = smtplib.SMTP(host='smtp.gmail.com', port='587')

    def __init__(self, email_target, message):
        '''
        Creates an instance of an EmailSender object, storing the email_target and message variables
        @precondition none
        @postcondition self.email_target == email_target, self.message == message
        '''
        self.email_target = email_target
        self.message = message
    
    def send_email(self, email_server):
        '''
        Sends the message stored in this class to the target email
        @precondition none
        @postcondition the email is sent to the target
        @return returns a dictionary containing all emails that rejected delivery of the message.
        ''' 
        email_server.starttls()
        email_server.login('steamscoutnotify@gmail.com', 'SteamScoutNotify')
        
        self.message['To']=self.email_target
        
        result = email_server.send_message(self.message)
        email_server.quit()
        
        return result;
        
        