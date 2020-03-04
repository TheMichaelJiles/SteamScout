'''
Created on Mar 3, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

class GameRequestAPI(object):
    '''
    Has the ability to make a request to the steam api for a particular game.
    The request gives a new game with all updated information.
    '''

    def __init__(self, steam_id):
        '''
        Initializes the data needed to make a game information request to the steam api.
        
        @param steam_id : integer - the steam id of the game to request information for.
        '''
        self.steam_id = steam_id
        
    def make_request(self, test_mode = False):
        '''
        Makes the request to the api and gets the newly updated information.
        
        @param test_mode : boolean - whether or not to run this request in test mode.
        
        @return: A dictionary with new information for the this game request's game.
        '''
        return None
        