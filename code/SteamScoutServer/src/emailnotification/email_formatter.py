'''
Created on Apr 8, 2020

@author: Michael Jiles
'''
from email.mime.text import MIMEText;
from email.mime.multipart import MIMEMultipart;
from dataupdates.fileaccess import FileAccess
import os
import json

class EmailFormatter(object):
    '''
    classdocs
    '''
    
    def __init__(self, steam_id, current_price, initial_price, steam_link):
        '''
        Constructor
        '''
        self.steam_id = steam_id;
        self.steam_game_name = "game not found";
        
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as jsonfile:
            game_table = FileAccess.read_game_table(lambda jsonfile: self._read_data(jsonfile), 'game_table.json')
            self.steam_game_name = game_table[str(steam_id)]['title']
        
        self.current_price = current_price;
        self.initial_price = initial_price;
        self.steam_link = steam_link;
    
    def format_for_email(self):
        '''
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
                       Here is the <a href=""" + self.steam_link + """>link</a> to buy it.
                    </p>
                </body>
            </html>
        """
        
        body = MIMEText(html, 'html')
        
        message.attach(body)
        return message
    
    def _read_data(self, file):
        return json.load(file)
        