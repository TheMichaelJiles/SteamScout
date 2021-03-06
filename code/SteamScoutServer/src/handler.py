'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''
from server_requests.servicefactory import ServiceFactory

class ClientHandler(object):
    '''
    Handles a client's incoming message. This class
    should have the capability to run on its own thread.
    Each connection made by a client should be handled by 
    a ClientHandler object.
    '''

    def __init__(self, client_json):
        '''
        Creates a new ClientHandler object that is
        able to process the client's request.
        
        @param client_json : dictionary - The json that the client sent to this server.
        '''
        self.client_json = client_json
        
    def process_request(self, api_handler, test_mode = False):
        '''
        Processes the client's request and returns the resulting
        json message to send back to the client.
        
        @return: The response to send back to the client.
        '''
        service = ServiceFactory.create_service(self.client_json, api_handler)
        return service.process_service(test_mode)
    