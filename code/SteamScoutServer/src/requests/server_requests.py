'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''
from requests.account import AccountCreator
from requests.validation import UserLogin
from requests.games import GameFetcher

class ServiceFactory(object):
    '''
    Creates different services depending on
    what is requested by the client.
    '''

    @classmethod
    def create_service(cls, client_json):
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
        else:
            raise TypeError(f'Invalid Service Type: {service_type}')