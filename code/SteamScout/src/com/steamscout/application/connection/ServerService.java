package com.steamscout.application.connection;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public abstract class ServerService<T> {

	private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";
	
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
	
	protected abstract T interpretJsonString(String json);
	
	protected abstract String getSendingJsonString();
	
}
