'''
Created on Apr 19, 2020

@author: Nathaniel Lightholder
'''
import unittest
from dataupdates.fileaccess import FileAccess


class Test_accessfile(unittest.TestCase):

    def test_access_fileShouldReturnValueOfPassedInFunction(self):
        number = FileAccess.access_file((int("7")))
        
        self.assertEquals(number, 7)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.test_access_file']
    unittest.main()