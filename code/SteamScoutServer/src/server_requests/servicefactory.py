'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''
from server_requests.accountcreator import AccountCreator
from server_requests.userlogin import UserLogin
from server_requests.gamefetcher import GameFetcher
from server_requests.notification import Notification
from server_requests.steamwishlistlink import SteamWishlistLink
from server_requests.watchlistaddition import WatchlistAddition
from server_requests.watchlistmodification import WatchlistModification
from server_requests.watchlistremoval import WatchlistRemoval
from server_requests.watchlistgamefetcher import WatchlistGameFetcher

class ServiceFactory(object):
    '''
    Creates different services depending on
    what is requested by the client.
    '''

    @classmethod
    def create_service(cls, client_json, api_handler):
        '''
        Depending on the type, it will create a 
        createaccount service, authentication service,
        gamefetching service, etc.
        
        @param client_json : dictionary - The json object that was sent from the client to the server.
        @return The appropriate service that can handle what the client is requesting.
        '''
        service_type = client_json['type']
        if service_type == 'create_account':
            return AccountCreator(client_json['data']['user']['username'], client_json['data']['user']['password'], client_json['data']['user']['email'])
        if service_type == 'authenticate':
            return UserLogin(client_json['data']['user']['username'], client_json['data']['user']['password']) 
        if service_type == 'fetch_games':
            return GameFetcher()
        if service_type == 'fetch_watchlist':
            return WatchlistGameFetcher(client_json['data']['user']['username'])
        if service_type == 'check_notifications':
            return Notification(client_json['data']['user']['username'])
        if service_type == 'link_steam':
            return SteamWishlistLink(client_json['data']['user']['username'], client_json['data']['user']['steamid'], api=api_handler)
        if service_type == 'watchlist_addition':
            return WatchlistAddition(client_json['data']['user']['username'], client_json['data']['steamid'])
        if service_type == 'watchlist_modification':
            return WatchlistModification(client_json['data']['user']['username'], client_json['data']['game']['steamid'], client_json['data']['game']['onsaleselected'], client_json['data']['game']['targetprice'], client_json['data']['game']['targetpriceselected'])
        if service_type == 'watchlist_removal':
            return WatchlistRemoval(client_json['data']['user']['username'], client_json['data']['steamid'])
        else:
            raise TypeError(f'Invalid Service Type: {service_type}')
        
        
