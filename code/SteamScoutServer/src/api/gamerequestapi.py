'''
Created on Mar 21, 2020

@author: luke
'''

class GameRequestAPI(object):
    '''
    This is the api service that gets the updated information for a single
    steam game. Gets the updated pricing information for a game.
    '''

    def __init__(self, steamid, api=None):
        '''
        Sets of the id of the steam game to get new information for. The api
        parameter is the api.apihandler.APIHandler object that is responsible for
        making sure too many calls are not made.
        
        @param steamid : the id of the steam game to get new information for
        @param api : the api.apihandler.APIHandler that handles actually making the request.
        '''
        self.steamid = steamid
        self.api = api
        
    def get_info(self, test_mode=False):
        '''
        Gets the updated pricing information for a game. Depending on what the value of test_mode
        is, actually makes the api call.
        
        @param test_mode : whether or not to run the service in test mode.
        @return the updated pricing information.
        '''
        service = _FakeGameRequestService() if test_mode else _GameRequestService(self.api)
        return service.attempt_get_info(self.steamid)
        
class _FakeGameRequestService(object):
    '''
    This is the fake request service used for testing that does not actually make any api calls.
    It just return dummy information that simulates what the get_info method may actually return with
    an api call.
    '''
    
    def attempt_get_info(self, steamid):
        '''
        Attempts to get the new pricing information for the game with the specified steam id.
        It only returns information if steamid is 3 or 4. This is because the games in the game_table_test.json
        with steamids 3 and 4 are the ones chosen for testing this service. In the json file, they have prices values of
        0 and onsale values of False. The test works by using this faked out service and setting the information in the table
        through the dataupdates.watchlistupdater.GameUpdater object. Then when the test is validated, then the information is reset
        to ensure subsequent tests in the future still pass.
        
        @param steamid : The steamid of the game to get information for.
        @return the faked out information used for testing.
        '''
        if steamid == 3:
            result = {'steamid': 3, 'initialprice': 39.99, 'actualprice': 39.99, 'onsale': False}
        elif steamid == 4:
            result = {'steamid': 4, 'initialprice': 39.99, 'actualprice': 29.99, 'onsale': True}
        else:
            result = None
        return result
        
class _GameRequestService(object):
    '''
    The actual production request service. This service actually makes the api call.
    For more than 175 repeated calls, the attempt may hang for up to five minutes as to not
    exceed api call limits.
    '''
    
    def __init__(self, api):
        '''
        Constructs the request service with the specified api.apihandler.APIHandler object
        that ensures api call limits are not exceeded.
        
        @param api : the api.apihandler.APIHandler object.
        '''
        self.api = api
    
    def attempt_get_info(self, steamid):
        '''
        Makes the attempt to get the info for the specified game. Api call limits will not be exceeded.
        It returns a dictionary that includes the following properties: steamid, initialprice, actualprice,
        and onsale. These properties are updated and current from the steam database.
        
        @param steamid : The id of the game to get new information for.
        @return the newly updated information for the game.
        '''
        url = f'https://store.steampowered.com/api/appdetails?appids={steamid}'
        result = self.api.make_request(url)
        while not result['was_successful']:
            result = self.api.make_request(url)
        received_json = result['json']
        
        if received_json is None:
            return None
        if not received_json['success']:
            return None
        if 'data' not in received_json:
            return None
        if 'price_overview' not in received_json['data']:
            return None
        
        overview = received_json['data']['price_overview']
        
        return {'steamid': steamid,
                'initialprice': overview['initial'] / 100,
                'actualprice': overview['final'] / 100,
                'onsale': overview['discount_percent'] > 0}
    