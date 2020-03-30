package com.steamscout.application.connection;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * This service contains functionality to send and receive
 * json to and from the server.
 * 
 * Children need only specify the json string to send to the
 * server and how to interpret the json results. Then, they may
 * call send() to receive their interpreted json data from the server.
 * 
 * @author Luke Whaley
 *
 * @param <T> the result of the interpretted data from the server.
 */
public abstract class ServerService<T> {

	private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";
	
	/**
	 * Sends getSendingJsonString() to the server. The server's response
	 * is given to interpretJsonString(String).
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return interpretJsonString(String)
	 */
	protected T send() {
		try (Context context = ZMQ.context(1);
				Socket socket = context.socket(SocketType.REQ)) {
			socket.connect(HOST_PORT_PAIR);
			String sendingJson = this.getSendingJsonString();
			socket.send(sendingJson.getBytes(ZMQ.CHARSET), 0);
			byte[] serverResponseBytes = socket.recv(0);
			String receivingJson = new String(serverResponseBytes, ZMQ.CHARSET);
			
			return this.interpretJsonString(receivingJson);
		}
	}
	
	/**
	 * Defines the behavior for how the data received back from
	 * the server is interpreted.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param json the json received from the server.
	 * @return the interpreted data.
	 */
	protected abstract T interpretJsonString(String json);
	
	/**
	 * Defines the string that is sent to the server as the request.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the string that is sent to the server as the request.
	 */
	protected abstract String getSendingJsonString();
	
}
