package com.steamscout.application.connection;

import org.json.JSONObject;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.connection.interfaces.CreateAccountService;
import com.steamscout.application.model.user.Credentials;

public class ServerCreateAccountService implements CreateAccountService {

	private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";
	
	@Override
	public void createAccount(Credentials credentials, String email) throws InvalidAccountException {
		try (Context context = ZMQ.context(1);
				Socket socket = context.socket(SocketType.REQ)) {
			socket.connect(HOST_PORT_PAIR);
			System.out.println("Initiating Server Account Creation Service");
			
			String sendingJson = this.getJsonString(credentials, email);
			socket.send(sendingJson.getBytes(ZMQ.CHARSET), 0);
			System.out.println("Sent the following json");
			System.out.println(new JSONObject(sendingJson).toString(4));
			
			byte[] serverResponseBytes = socket.recv(0);
			String receivingJson = new String(serverResponseBytes, ZMQ.CHARSET);
			System.out.println("Received the following json");
			System.out.println(new JSONObject(receivingJson).toString(4));
			
			this.interpretJsonString(credentials, receivingJson);
		}
	}

	protected void interpretJsonString(Credentials credentials, String receivingJson) throws InvalidAccountException {
		JSONObject root = new JSONObject(receivingJson);
		boolean isCreated = root.getBoolean("result");
		if (!isCreated) {
			throw new InvalidAccountException(credentials);
		}
	}

	protected String getJsonString(Credentials credentials, String email) {
		JSONObject user = new JSONObject();
		user.put("username", credentials.getUsername());
		user.put("password", credentials.getPassword());
		user.put("email", email);
		
		JSONObject data = new JSONObject();
		data.put("user", user);
		
		JSONObject root = new JSONObject();
		root.put("type", "create_account");
		root.put("data", data);
		
		return root.toString();
	}

}
