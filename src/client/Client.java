package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * @author Patrick Powell (000815382)
 * @author Stevie Brinson (001068791)
 */

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
	}
	
	public void closeClient() {
		try {
			PClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runP2P() {
		int PortNumber = 15000;
	    
	    ServerSocket PServer = null;
	    
	    System.out.println("Waiting for peer connection");
	    
	    while(true) {
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
	}
	
	public void send(String method, String rfc, String port) {
		OutputStream outToServer = null;
		try {
			outToServer = PClient.getOutputStream();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
         DataOutputStream out = new DataOutputStream(outToServer);
         try {
			out.writeUTF(method + " " + rfc + "\n" + port);
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
         InputStream serverStream = null;
		try {
			serverStream = PClient.getInputStream();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
         DataInputStream in = new DataInputStream(serverStream);
         try {
			System.out.println(in.readUTF());
		}
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getList() {
		OutputStream outToServer = null;
		try {
			outToServer = PClient.getOutputStream();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
         DataOutputStream out = new DataOutputStream(outToServer);
         try {
			out.writeUTF("LIST");
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
         InputStream serverStream = null;
		try {
			serverStream = PClient.getInputStream();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
         DataInputStream in = new DataInputStream(serverStream);
         try {
			System.out.println(in.readUTF());
		}
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		
		String msg = "ADD RFC2345\n15000";//args[0];
		char parser = '\n';
		boolean closed = false;
		String method = null;
		
		if (!msg.equals("LIST")) method = msg.substring(0, msg.indexOf(' '));
		else method = msg;
			    
	    if (method.equals("ADD")) {
	    	msg = msg.substring(msg.indexOf(' ') + 1);
			String rfc = msg.substring(0, msg.indexOf(parser));
			msg = msg.substring(msg.indexOf(parser) + 1);
			String port = msg;
			
			client.send(method,rfc,port);
	    	
	    	client.runP2P();
	    	
	    	client.closeClient();
	    	closed = true;
	    }
	    else if (method.equals("LOOKUP")) {
	    	msg = msg.substring(msg.indexOf(' ') + 1);
			String rfc = msg.substring(0, msg.indexOf(parser));
	    	client.send(method, rfc, Integer.toString(15000));
	    }
	    else if (method.equals("LIST")) {
	    	client.getList();
	    }
	    
	    if (!closed) client.closeClient();
	}

}
