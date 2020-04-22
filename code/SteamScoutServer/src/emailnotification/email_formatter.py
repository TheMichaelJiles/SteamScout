'''
Created on Apr 8, 2020

@author: Michael Jiles
'''
from email.mime.text import MIMEText;
from email.mime.multipart import MIMEMultipart;
import os
import json

class EmailFormatter(object):
    '''
    Class responsible for formatting an email notification
    '''
    
    def __init__(self, steam_id, current_price, initial_price):
        '''
        Creates a new EmailFormatter
        @precondition none
        @postcondition self.steam_id == steam_id, self.current_price == current_price,
                       self.initial_price == initial_price, self.steam_game_name == name pulled from game_table with the steam_id.
        '''
        self.steam_id = steam_id;
        self.steam_game_name = "game not found";
        
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as jsonfile:
            game_table = json.load(jsonfile)
            self.steam_game_name = game_table[str(steam_id)]['title']
        
        self.current_price = current_price;
        self.initial_price = initial_price;
    
    def format_for_email(self):
        '''
        Formats a notification as follows:
        Hi!
        <game name> was on your watchlist, and has just gone on a <discount_percent>% discount, and is only $<current_price>!<br>
                       Here is the link to buy it.
        @precondition none
        @postcondition none
        @return the formatted email notification message.
        '''
        message = MIMEMultipart('alternative')
        message['Subject'] = self.steam_game_name + 'is on Sale for ' + str(self.current_price)
        message['From'] = "steamscoutnotify@gmail.com"
        
        html = """\
            <html>
                <head></head>
                <body>
                    <p>Hi!<br>
                       """ + self.steam_game_name + """ was on your watchlist, and has just gone on a """ + str(100 * (self.current_price / self.initial_price)) + """% discount, and is only $""" + str(self.current_price) + """!<br>
                       Here is the <a href=""" + "https://store.steampowered.com/app/" + str(self.steam_id) + """>link</a> to buy it.
                    </p>
                </body>
            </html>
        """
        
        body = MIMEText(html, 'html')
        
        message.attach(body)
        return message
        