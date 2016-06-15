package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	
    Socket PClient;

	public Client() {
		
		int PortNumber = 7734;
		
		PClient = null;
	    try {
	           PClient = new Socket("localhost", PortNumber);
	    }
	    catch (IOException e) {
	        System.out.println(e + " PClient failed");
	    }
	    
	    /*PortNumber = 15001;
	    
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
	    }*/
	}
	
	public void closeClient() {
		try {
			PClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send() {
		 OutputStream outToServer = null;
		try {
			outToServer = PClient.getOutputStream();
		} 
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         DataOutputStream out = new DataOutputStream(outToServer);
         try {
			out.writeUTF("Hello from " + PClient.getLocalSocketAddress());
		} 
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         InputStream serverStream = null;
		try {
			serverStream = PClient.getInputStream();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         DataInputStream in = new DataInputStream(serverStream);
         try {
			System.out.println("Server says " + in.readUTF());
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		
		client.send();
	    
	    client.closeClient();
	}

}
