package com.steamscout.application.test.model.api.apirequest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.APIRequest;

public class TestConstructor {

	private class TestAPI extends APIRequest {

		public TestAPI(String url) {
			super(url);
		}
		
		@Override
		public Object makeRequest() throws IOException {
			return null;
		}
		
	}
	
	@Test
	public void testNotAllowNullUrl() {
		assertThrows(IllegalArgumentException.class, () -> new TestAPI(null));
	}

}
