'''
Created on Mar 3, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

from api.steam_user_wishlist import WishlistRequestAPI

import json
import os

class SteamWishlistLink(object):
    '''
    This service gives the ability to link a steam wishlist with 
    a user's watchlist. It has two modes. Production mode actually calls
    the api and utilizes the database. Test mode does not call the api and does
    not use the database; instead, it uses a predefined wishlist specified in
    steam_user_wishlist._FakeWishlistRequestService. 
    '''

    def __init__(self, user_name, user_steam_id, id_already_saved, id_should_save):
        '''
        Initializes the parameters needed to link a steam wishlist.
        
        @param user_name : string - the user's username.
        @param user_steam_id : integer - the user's steam id for their steam account.
        @param id_already_saved : boolean - whether or not the user's steam id for their account is already saved.
        @param id_should_save : boolean - whether or not to save the user's steam id in the database.
        '''
        self.user_name = user_name
        self.user_steam_id = user_steam_id
        self.id_already_saved = id_already_saved
        self.id_should_save = id_should_save
        
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
        service = _FakeWishlistLinkingService() if test_mode else _WishlistLinkingService()
        return service.link_wishlist(self.user_name, self.user_steam_id, self.id_already_saved, self.id_should_save)
        
class _FakeWishlistLinkingService(object):
    '''
    This wishlist linking service is used for testing purposes. All system data accessed from
    within this class is pulled from other fakes or through the json files within the test_data directory.
    '''
    
    def link_wishlist(self, user_name, user_steam_id, id_already_saved, id_should_save):
        '''
        Links the wishlist of the specified user with the user's watchlist. Performs the wishlist api call
        through the use of the steam_user_wishlist._FakeWishlistRequestService. Reads all data from/to the
        json files within the test_data directory. Since this is used for testing purposes. No writing to the test
        files will occur. We can't write to the test files then repeated calls to the same tests will start to fail.
        The test expects certain information to be in the test files and certain information to not be in there. Adding
        to the test files when running the unit tests will break these expectations for subsequent runs of the unit tests.
        
        @param user_name : string - the user's username.
        @param user_steam_id : integer - the user's steam id for their steam account.
        @param id_already_saved : boolean - whether or not the user's steam id for their account is already saved.
        @param id_should_save : boolean - whether or not to save the user's steam id in the database.
        
        @return: The json string to send back to the client.
        '''      
        
        # This starts the main steam linking process.
        wishlist_service = WishlistRequestAPI(user_steam_id)
        wishlist_games = wishlist_service.fetch_wishlist(test_mode = True)
          
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table_test.json'), 'r') as watchlist_file:
            watchlist_data = json.load(watchlist_file)
            for game in wishlist_games:
                can_add = True
                for item in watchlist_data:
                    if item['username'] == user_name and item['steamid'] == game['steamid']:
                        can_add = False
                if can_add:
                    watchlist_data.append({"id": len(watchlist_data) + 1, 
                                           "steamid": game['steamid'],
                                           "username": user_name,
                                           "targetprice_criteria": 0.0,
                                           "onsale_selected": False,
                                           "targetprice_selected": False})
            
        watchlist = []   
        for item in watchlist_data:
            if item['username'] == user_name:
                steamid = item['steamid']
                with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table_test.json'), 'r') as game_file:
                    game_data = json.load(game_file)
                    for game in game_data:
                        if game['steamid'] == steamid:
                            watchlist.append({'steamid': steamid,
                                              'title': game['title'],
                                              'initialprice': game['initialprice'],
                                              'actualprice': game['actualprice'],
                                              'onsale': game['onsale'],
                                              'targetprice_criteria': item['targetprice_criteria'],
                                              'onsale_selected': item['onsale_selected'],
                                              'targetprice_selected': item['targetprice_selected']})
                       
        return {"result": True, "watchlist": watchlist}
        
        
class _WishlistLinkingService(object):
    '''
    This wishlist linking service is for production use. It makes all api calls through the
    use of http requests. Also, any data read or written to is performed through the database.
    '''
    
    def link_wishlist(self, user_name, user_steam_id, id_already_saved, id_should_save):
        '''
        Links the wishlist of the specified user with the user's watchlist. Performs the wishlist api call
        through the use of the steam_user_wishlist._WishlistRequestService. Reads/Writes all data from/to the
        database.
        
        @param user_name : string - the user's username.
        @param user_steam_id : integer - the user's steam id for their steam account.
        @param id_already_saved : boolean - whether or not the user's steam id for their account is already saved.
        @param id_should_save : boolean - whether or not to save the user's steam id in the database.
        
        @return: The json string to send back to the client.
        '''
        wishlist_service = WishlistRequestAPI(user_steam_id)
        wishlist_games = wishlist_service.fetch_wishlist(test_mode = True)
          
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'r') as watchlist_file:
            watchlist_data = json.load(watchlist_file)
            for game in wishlist_games:
                can_add = True
                for item in watchlist_data:
                    if item['username'] == user_name and item['steamid'] == game['steamid']:
                        can_add = False
                if can_add:
                    watchlist_data.append({"id": len(watchlist_data) + 1, 
                                           "steamid": game['steamid'],
                                           "username": user_name,
                                           "targetprice_criteria": 0.0,
                                           "onsale_selected": False,
                                           "targetprice_selected": False})
        watchlist = []   
        for item in watchlist_data:
            if item['username'] == user_name:
                steamid = item['steamid']
                with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'game_table.json'), 'r') as game_file:
                    game_data = json.load(game_file)
                    for game in game_data:
                        if game['steamid'] == steamid:
                            watchlist.append({'steamid': steamid,
                                              'title': game['title'],
                                              'initialprice': game['initialprice'],
                                              'actualprice': game['actualprice'],
                                              'onsale': game['onsale'],
                                              'targetprice_criteria': item['targetprice_criteria'],
                                              'onsale_selected': item['onsale_selected'],
                                              'targetprice_selected': item['targetprice_selected']})
                            
        with open(os.path.join(os.path.dirname(__file__), '..', 'test_data', 'watchlist_table.json'), 'w') as watchlist_file:
            json.dump(watchlist_data, watchlist_file)
            
        return {"result": True, "watchlist": watchlist}
    
