package application;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.*;

public class Client extends AbstractClient {
	public Client(String host, int port) {
		super(host, port);
	}

	public void handleMessageFromServer(Object msg) {
	}

	public Object Request(ArrayList<String> request) {
		try {
			return this.sendToServer(request);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected void finalize() {
		close(); 
	}

	public void open() {
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
	}
}