package client;

import java.io.IOException;
import java.net.Socket;

public class Client {

	public Client() {
		
		int PortNumber = 15000;
		
	    Socket PClient;
	    try {
	           PClient = new Socket("Machine name", PortNumber);
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Client();
	}

}
