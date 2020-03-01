'''
Created on Mar 1, 2020

@author: Luke Whaley, Nathan Lightholder, Michael Jiles
'''

import zmq
import json

class Server:
    '''
    Contains functionality to start and run the SteamScout back-end
    server. The server is bound on localhost port 5555.
    '''
    
    def __init__(self):
        '''
        Initializes this server. It is bound to localhost
        port 5555. The one created field is self.socket,
        this socket represents the server's socket and all incoming
        connections must be made to this socket.
        '''
        context = zmq.Context()
        self.socket = context.socket(zmq.REP)
        self.socket.bind('tcp://127.0.0.1:5555')
        
    def start(self):
        '''
        Starts this server. It begins listening for incoming connections.
        The incoming connections are expected to be in JSON format. The
        server handles the data it received and sends back a response
        in JSON format.
        '''
        print('SteamScoutServer is started.')
        print('Listening for connections..') 
        json_message = self.socket.recv_string()
        
        message = json.loads(json_message)
        print('Received Message')
        
        # We'll do something with the message.
        
        response = ''
        json_response = json.dumps(response)
        self.socket.send_string(json_response)

if __name__ == '__main__':
    '''
    Initializes the Server object and starts the server.
    '''
    steamscout_server = Server()
    steamscout_server.start()
    
