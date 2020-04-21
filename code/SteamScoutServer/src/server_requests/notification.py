'''
Created on Mar 3, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os
from dataupdates.fileaccess import FileAccess

class Notification(object):
    '''
    This notification service takes a user's watchlist and evaluates each game.
    For each game, the corresponding game's record is evaluated to see if the
    notification criteria is met. If it is, then that game is added to the list of
    notifications that the user should receive.
    '''

    def __init__(self, user_name):
        '''
        Initializes the data needed for the notification service. This username is 
        needed to find the game's on the specified user's watchlist.
        
        @param user_name : string - the user's username.
        '''
        self.user_name = user_name
        
    def process_service(self, test_mode = False):
        '''
        Performs the notification service. The games on the user's watchlist that 
        have their notification criteria met are marked as a game that should have 
        a notification sent out.
        
        @postcondition: process_service()['notifications'] == list of all notifications for the user. It may be empty.
        
        @param test_mode : boolean - whether or not to run this service in test mode.
        
        @return: The json string containing the notification data to send back to the client.
        '''
        service = _NotificationService()
        watchlist_filename = 'watchlist_table_test.json' if test_mode else 'watchlist_table.json'
        game_filename = 'game_table_test.json' if test_mode else 'game_table.json'
        return service.attempt_fetch_notifications(self.user_name, watchlist_filename, game_filename)
    
class _NotificationService(object):
    '''
    This notification service is used for production purposes. All data
    is read from the database.
    '''
    
    def attempt_fetch_notifications(self, user_name, watchlist_filename, game_filename):
        '''
        Attempts to fetch notifications for the specified user. This is the 
        production implementation. Therefore, all data is read from the database.
        
        @param user_name : string - the user's username.
        
        @return: The json string to send back to the user.
        '''
        id_info = []
        watchlist_data = FileAccess.read_watchlist_table(lambda watchlist_file: self._read_data(watchlist_file), watchlist_filename)
        for key in watchlist_data:
            if watchlist_data[key]['username'] == user_name:
                id_info.append({'steamid': watchlist_data[key]['steamid'],
                                'onsale_selected': watchlist_data[key]['onsale_selected'],
                                'targetprice_selected': watchlist_data[key]['targetprice_selected'],
                                'targetprice_criteria': watchlist_data[key]['targetprice_criteria']})
                    
        game_notifications = []
        game_data = FileAccess.read_watchlist_table(lambda game_file: self._read_data(game_file), game_filename)
        for info in id_info:
            onsale = info['onsale_selected'] and game_data[str(info['steamid'])]['onsale']
            below_criteria = info['targetprice_selected'] and game_data[str(info['steamid'])]['actualprice'] <= info['targetprice_criteria']
            if onsale or below_criteria:
                game = {}
                game['steamid'] = info['steamid']
                game.update(game_data[str(info['steamid'])])
                game_notifications.append(game)
        
        return {'notifications': game_notifications}
    
    def _read_data(self, file):
        return json.load(file)
    

    