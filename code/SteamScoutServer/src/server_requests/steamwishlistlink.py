'''
Created on Mar 3, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import os, json

from api.steam_user_wishlist import WishlistRequestAPI
from server_requests.watchlistaddition import WatchlistAddition
from server_requests.watchlistgamefetcher import WatchlistGameFetcher

class SteamWishlistLink(object):
    '''
    This service gives the ability to link a steam wishlist with 
    a user's watchlist. It has two modes. Production mode actually calls
    the api and utilizes the database. Test mode does not call the api and does
    not use the database; instead, it uses a predefined wishlist specified in
    steam_user_wishlist._FakeWishlistRequestService. 
    '''

    def __init__(self, user_name, user_steam_id, api=None):
        '''
        Initializes the parameters needed to link a steam wishlist.
        
        @param user_name : string - the user's username.
        @param user_steam_id : integer - the user's steam id for their steam account.
        '''
        self.user_name = user_name
        self.user_steam_id = user_steam_id
        self.api = api
        
    def process_service(self, test_mode = False):
        '''
        Performs the service that links the user's steam wishlist with their watchlist.
        There are two implementations. test_mode does not make api calls or access the database.
        The returned json should be sent back to the client as it contains the client's updated watchlist.
        Steam games that were found to be on the user's wishlist that are already on their watchlist will
        not be duplicated.
        
        @postcondition: if any errors occur, process_service()["result"] == False
                        if the process occurs correctly, process_service()["result"] == True
                        if the process occurs correctly, process_service()["watchlist"] == the user's newly updated watchlist.
                        
        @return: The json string to return back to the client. It contains a result value, and the user's newly updated watchlist.
        '''
        service = _WishlistLinkingService(self.api)
        return service.link_wishlist(self.user_name, self.user_steam_id, test_mode)
        
class _WishlistLinkingService(object):
    '''
    This wishlist linking service is for production use. It makes all api calls through the
    use of http requests. Also, any data read or written to is performed through the database.
    '''
    
    def __init__(self, api):
        self.api = api
    
    def link_wishlist(self, user_name, user_steam_id, test_mode):
        '''
        Links the wishlist of the specified user with the user's watchlist. Performs the wishlist api call
        through the use of the steam_user_wishlist._WishlistRequestService. Reads/Writes all data from/to the
        database.
        
        @param user_name : string - the user's username.
        @param user_steam_id : integer - the user's steam id for their steam account.
        
        @return: The json string to send back to the client.
        '''
        wishlist_service = WishlistRequestAPI(user_steam_id, api=self.api)
        wishlist_games = wishlist_service.fetch_wishlist(test_mode)
        print(wishlist_games)
        was_successful = wishlist_games['was_successful']
        returning_json = {"result": was_successful}
        
        if was_successful:
            api_json = wishlist_games['json']
            for key in api_json:
                addition_service = WatchlistAddition(user_name, int(key))
                addition_service.process_service(test_mode)
        
        watchlist_game_fetch = WatchlistGameFetcher(user_name)
        results = watchlist_game_fetch.process_service(test_mode)
            
        returning_json['watchlist'] = results['games_on_watchlist']
        
        return returning_json
    
