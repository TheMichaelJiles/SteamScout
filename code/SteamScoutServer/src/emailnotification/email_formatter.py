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
    
    def __init__(self, steam_id, current_price, initial_price, steam_link, test_mode):
        '''
        Constructor
        '''
        self.steam_id = steam_id;
        self.steam_game_name = "game not found";
        
        
        game_table = FileAccess.read_game_table(lambda jsonfile: self._read_data(jsonfile), 'game_table_test.json' if test_mode else 'game_table.json')
        self.steam_game_name = game_table[str(steam_id)]['title']
        
        self.current_price = current_price;
        self.initial_price = initial_price;
        self.steam_link = steam_link;
    
    def format_for_email(self):
        '''
        '''
        message = MIMEMultipart('alternative')
        message['Subject'] = self.steam_game_name + ' is on Sale for $' + str(self.current_price)
        message['From'] = "steamscoutnotify@gmail.com"
        
        if self.initial_price <= 0:
            discount = 0
        else:
            discount = self.current_price / self.initial_price
        html = """\
            <html>
                <head></head>
                <body>
                    <p>Hi!<br><br>
                       """ + self.steam_game_name + """ is on your watchlist, and has just gone on a """ + str(int(100 * discount)) + """% discount, and is only $""" + str(self.current_price) + """!<br><br>
                       Buy it on <a href=""" + self.steam_link + """>Steam</a>.
                    </p>
                </body>
            </html>
        """
        
        body = MIMEText(html, 'html')
        
        message.attach(body)
        return message
    
    def _read_data(self, file):
        return json.load(file)
        