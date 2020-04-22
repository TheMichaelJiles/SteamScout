'''
Created on Apr 8, 2020

@author: Michael Jiles
'''
import unittest
import json
import os
from emailnotification.email_formatter import EmailFormatter


class Test(unittest.TestCase):


    def test_format_for_email(self):
        steam_id = 1
        with open(os.path.join(os.path.dirname(__file__), '..', '..', 'test_data', 'game_table_test.json'), 'r') as jsonfile:
            game_table = json.load(jsonfile)
            title = game_table[str(steam_id)]['title']
        
        current_price = 25.00
        initial_price = 60.00
        steam_link = "https://store.steampowered.com/app/" + str(steam_id)
        
        formatter = EmailFormatter(steam_id, current_price, initial_price, steam_link, True)
        message = formatter.format_for_email()
        expected_subject =  title + ' is on Sale for $' +str(current_price)
        print(message)
        print(message.get_payload())
        self.assertEqual(expected_subject, message['Subject'])

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()