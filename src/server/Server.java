package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public Server() {
		int PortNumber = 15000;
		
		ServerSocket PServer = null;
	    try {
	       PServer = new ServerSocket(PortNumber);
	        }
	        catch (IOException e) {
	           System.out.println(e);
	        }
	    
	    Socket clientSocket = null;
	    try {
	       clientSocket = PServer.accept();
	        }
	    catch (IOException e) {
	       System.out.println(e);
	    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
	}

}
