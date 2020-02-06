package com.steamscout.application.model.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

/**
 * An abstract api class that allows abstracts the api process away.
 * A call to the poll() method can be utilized any way a user sees fit
 * in the makeRequest() method.
 * 
 * @author Thomas Whaley
 *
 */
public abstract class APIRequest {

	private HttpClient client;
	private HttpGet request;
	
	protected APIRequest(String apiUrl) {
		if (apiUrl == null) {
			throw new IllegalArgumentException("apiUrl should not be null.");
		}
		
		this.client = HttpClientBuilder.create().build();
		this.request = new HttpGet(apiUrl);
	}
	
	/**
	 * Makes the api request. When implementing this method, a JSONObject can be 
	 * retrieved by polling the api using the poll() method. Then, manipulate that
	 * JSONObject and retrieve any data that you can.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @throws IOException if an error occurs making the request.
	 * @return result the formatted data from the request.
	 */
	public abstract Object makeRequest() throws IOException;
	
	protected JSONObject pollApi() throws IOException {
		String result = null;
		
		HttpResponse response = this.client.execute(this.request);
		HttpEntity entity = response.getEntity();	
		if (entity != null) {
			try (InputStream inputStream = entity.getContent()) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
					StringBuilder builder = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						builder.append(line + System.lineSeparator());
					}
					result = builder.toString();
				}
			}
		}

		return new JSONObject(result);
	}
}
