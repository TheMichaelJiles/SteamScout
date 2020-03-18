'''
Created on Mar 3, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import json
import os

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
        service = _FakeNotificationService() if test_mode else _NotificationService()
        return service.attempt_fetch_notifications(self.user_name)
    
class _FakeNotificationService(object):
    '''
    This notification service is for testing purposes. All data accessed is read
    from the json files within the test_data directory.
    '''
    
    def attempt_fetch_notifications(self, user_name):
        '''
        Attempts to fetch the notifications for the specified user. This is a test
        implementation. So, none of the data in the database is touched. Data is read 
        strictly from the test_data/watchlist_table.json file.
        
        @param user_name : string - the user's username.
        
        @return: The json string to send back to the user.
        '''
        game_info = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as watchlist_file:
            watchlist_data = json.load(watchlist_file)
            for item in watchlist_data:
                if item['username'] == user_name:
                    info = {}
                    info['steamid'] = item['steamid']
                    info['onsale_selected'] = item['onsale_selected']
                    info['targetprice_selected'] = item['targetprice_selected']
                    info['targetprice_criteria'] = item['targetprice_criteria']
                    game_info.append(info)
                    
        game_notifications = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table_test.json'), 'r') as game_file:
            game_data = json.load(game_file)
            for game_information in game_info:
                for game in game_data:
                    if game['steamid'] == game_information['steamid']:
                        if game_information['onsale_selected'] and game['onsale']:
                            game_notifications.append(game)
                        elif game_information['targetprice_selected'] and game['actualprice'] <= game_information['targetprice_criteria']:
                            game_notifications.append(game)
        
        return {'notifications': game_notifications}
    
class _NotificationService(object):
    '''
    This notification service is used for production purposes. All data
    is read from the database.
    '''
    
    def attempt_fetch_notifications(self, user_name):
        '''
        Attempts to fetch notifications for the specified user. This is the 
        production implementation. Therefore, all data is read from the database.
        
        @param user_name : string - the user's username.
        
        @return: The json string to send back to the user.
        '''
        game_info = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'r') as watchlist_file:
            watchlist_data = json.load(watchlist_file)
            for item in watchlist_data:
                if item['username'] == user_name:
                    info = {}
                    info['steamid'] = item['steamid']
                    info['onsale_selected'] = item['onsale_selected']
                    info['targetprice_selected'] = item['targetprice_selected']
                    info['targetprice_criteria'] = item['targetprice_criteria']
                    game_info.append(info)
                    
        game_notifications = []
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as game_file:
            game_data = json.load(game_file)
            for game_information in game_info:
                for game in game_data:
                    if game['steamid'] == game_information['steamid']:
                        if game_information['onsale_selected'] and game['onsale']:
                            game_notifications.append(game)
                        elif game_information['targetprice_selected'] and game['actualprice'] <= game_information['targetprice_criteria']:
                            game_notifications.append(game)
        
        return {'notifications': game_notifications}

    