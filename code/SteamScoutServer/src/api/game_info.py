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
    
class _FakeGameRequestService(object):
    '''
    This game request service is used for testing purposes. No actual api
    calls are made within this class.
    '''
    
    def make_request(self, url):
        '''
        Testing implementation for the game request service. It always returns
        the constant list of dictionaries.
        
        @return: The constant list of dictionaries: [{},
                                                     {},
                                                     {},
                                                     ...]
        '''
        return []
        
class _WishlistRequestService(object):
    '''
    This game request service is used for production purposes. Api calls
    are made within this class. The api calls are throttled to avoid call limits.
    '''
    
    def make_request(self, url):
        '''
        Production implementation for the game request service. It fetches a json
        response from the given url. Each game in the response has its steamid and title 
        extracted, as well as its price info, and put in a dictionary; then, they are 
        added to a list and returned.
        
        @param url : string - the api url.
        
        @return: The list of game dictionaries that contain information about the games on the user's steam wishlist.
        '''
        
        return None
        