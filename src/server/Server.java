package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
	
	String[] peerList;
	String[] rfcList;
	int[] portList;
		
	ServerSocket PServer = null;
	
	public Server() {
		int PortNumber = 7734;
	    try {
	       PServer = new ServerSocket(PortNumber);
	        }
	        catch (IOException e) {
	           System.out.println(e + " PServer socket failed");
	        }
	    
	    /*Socket clientSocket = null;
	    try {
	       clientSocket = PServer.accept();
	        }
	    catch (IOException e) {
	       System.out.println(e + " PServer accept failed");
	    }*/
	}
	
	public void run() {
	      while(true)
	      {
	         try
	         {
	            System.out.println("Waiting for client on port " +
	            PServer.getLocalPort() + "...");
	            
	            Socket pSocket = PServer.accept();
	            System.out.println("Just connected to "
	                  + pSocket.getRemoteSocketAddress());
	            
	            DataInputStream in = new DataInputStream(pSocket.getInputStream());
	            
	            String msg = in.readUTF();
	            
	            System.out.println(msg);
	            
	            DataOutputStream out = new DataOutputStream(pSocket.getOutputStream());
	            
	            String method = getMethod(msg);
	            
	            if (method.equals("LOOKUP")) {
	            	if (getRFC(msg) == null) out.writeUTF("RFC not found\n");	            
	            }
	            
	            out.writeUTF("Closing connection to "
	              + pSocket.getLocalSocketAddress() + "\nGoodbye!");
	            
	            pSocket.close();
	         } catch(SocketTimeoutException s)
	         {
	            System.out.println("Socket timed out!");
	            break;
	         } catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         }
	      }
	}
	
	public String getMethod(String msg) {
		return msg.substring(0, msg.indexOf(' '));
	}
	
	public void addRFC(String msg) {
		char parser = '\n';
		int arrayCount = peerList.length;
		String peer = msg.substring(0, msg.indexOf(parser));
		msg = msg.substring(msg.indexOf(parser) + 1);
		String rfc = msg.substring(0, msg.indexOf(parser));
		msg = msg.substring(msg.indexOf(parser) + 1);
		String port = msg.substring(0, msg.indexOf(parser));
		
		peerList[arrayCount] = peer;
		rfcList[arrayCount] = rfc;
		portList[arrayCount] = Integer.parseInt(port);
	}
	
	public String getRFC(String msg) {
		for (int i=0; i<peerList.length;i++) {
			if (rfcList[i].equals(msg)) {
				String ret = peerList[i] + "\n" + portList; 
				return ret;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		
		server.run();
	}

}
