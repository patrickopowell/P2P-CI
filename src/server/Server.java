package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
	
	String[] peerList;
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
	
	public void run()
	   {
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
	            
	            System.out.println(in.readUTF());
	            
	            DataOutputStream out = new DataOutputStream(pSocket.getOutputStream());
	            
	            out.writeUTF("Thank you for connecting to "
	              + pSocket.getLocalSocketAddress() + "\nGoodbye!");
	            
	            pSocket.close();
	         }catch(SocketTimeoutException s)
	         {
	            System.out.println("Socket timed out!");
	            break;
	         }catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         }
	      }
	   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		
		server.run();
	}

}
