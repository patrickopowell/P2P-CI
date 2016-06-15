package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	
    Socket PClient;

	public Client() {
		
		int PortNumber = 15000;
		
		PClient = null;
	    try {
	           PClient = new Socket("localhost", PortNumber);
	    }
	    catch (IOException e) {
	        System.out.println(e + " PClient failed");
	    }
	    
	    PortNumber = 15001;
	    
	    ServerSocket PServer = null;
	    
	    try {
	       PServer = new ServerSocket(PortNumber);
	        }
	        catch (IOException e) {
	           System.out.println(e + " PServer socket failed");
	        }
	    
	    Socket clientSocket = null;
	    try {
	       clientSocket = PServer.accept();
	        }
	    catch (IOException e) {
	       System.out.println(e + " PServer accept failed");
	    }
	}
	
	public void closeClient() {
		try {
			PClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
	    
	    client.closeClient();
	}

}
