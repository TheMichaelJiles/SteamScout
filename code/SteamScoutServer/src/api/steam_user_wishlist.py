'''
Created on Mar 3, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

class WishlistRequestAPI(object):
    '''
    This api request contacts the steam api for a specified user's account steam id.
    It can return a list of dictionaries for each game that is in the user's steam wishlist.
    '''

    def __init__(self, user_steam_id, api=None):
        '''
        Initializes the data needed to perform the wishlist request.
        
        @param user_steam_id : integer - the user's steam account id.
        '''
        self.url = f'https://store.steampowered.com/wishlist/profiles/{user_steam_id}/wishlistdata/?p=0'
        self.api = api
        
    def fetch_wishlist(self, test_mode = False):
        '''
        Fetches the wishlist. test_mode can be toggled to swap between test/production execution.
        
        @postcondition: fetch_wishlist() == list of all game dictionaries from the steam wishlist.
                        The dictionaries have two keys: "steamid" and "title"
                        
        @param test_mode : boolean - whether or not to run this request in test mode.
        
        @return: The list of game dictionaries that contain data about the user's steam wishlist.
        '''
        service = _FakeWishlistRequestService() if test_mode else _WishlistRequestService(self.api)
        return service.make_request(self.url)
    
class _FakeWishlistRequestService(object):
    '''
    This wishlist request service is used for testing purposes. No actual api
    calls are made within this class.
    '''
    
    def make_request(self, url):
        '''
        Testing implementation for the wishlist request service. It always returns
        the constant list of dictionaries.
        
        @return: The constant list of dictionaries: [{"steamid": 23942, "title": "Jurassic Park"},
                                                     {"steamid": 19510, "title": "Home Alone: Revenge"},
                                                     {"steamid": 15498, "title": "Mr. Burger: Taste the Grease"}]
        '''
        return [{"steamid": 23942, "title": "Jurassic Park"},
                {"steamid": 19510, "title": "Home Alone: Revenge"},
                {"steamid": 15498, "title": "Mr. Burger: Taste the Grease"}]
        
class _WishlistRequestService(object):
    '''
    This wishlist request service is used for production purposes. Api calls
    are made within this class. The api calls are throttled to avoid call limits.
    '''
    
    def __init__(self, api):
        self.api = api
    
    def make_request(self, url):
        '''
        Production implementation for the wishlist request service. It fetches a json
        response from the given url. Each game in the resposne has its steamid and title 
        extracted and put in a dictionary; then, they are added to a list and returned.
        
        @param url : string - the api url.
        
        @return: The results of the api call
        '''
        return self.api.make_request(url)
    
