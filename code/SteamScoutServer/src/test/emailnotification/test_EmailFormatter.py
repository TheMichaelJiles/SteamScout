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
        
        current_price = 5.00
        initial_price = 10.00

        expected_subject =  title + ' is on Sale for $' +str(current_price)
        html = """\
            <html>
                <head></head>
                <body>
                    <p>Hi!<br>
                       """ + title + """ was on your watchlist, and has just gone on a """ + str(100 * (current_price / initial_price)) + """% discount, and is only $""" + str(current_price) + """!<br>
                       Here is the <a href=""" + "https://store.steampowered.com/app/" + str(steam_id) + """>link</a> to buy it.
                    </p>
                </body>
            </html>
        """
        
        formatter = EmailFormatter(steam_id, current_price, initial_price, test_mode = True)
        message = formatter.format_for_email()
        result_payload = message.get_payload(0)
        result_body = result_payload.get_payload()
        self.assertEqual(expected_subject, message['Subject'])
        self.assertEqual(result_body, html)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()