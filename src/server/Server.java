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
	
	int arrayCount;
		
	ServerSocket PServer = null;
	
	public Server() {
		int PortNumber = 7734;
	    try {
	       PServer = new ServerSocket(PortNumber);
	    }
	    catch (IOException e) {
	    	System.out.println(e + " PServer socket failed");
	    }
	    
	    peerList = new String[100];
	    rfcList = new String[100];
	    portList = new int[100];
	    
	    arrayCount = 0;
	}
	
	public void run() {
	      while(true)
	      {
	         try
	         {
	            Socket pSocket = PServer.accept();
	            
	            DataInputStream in = new DataInputStream(pSocket.getInputStream());
	            
	            String msg = in.readUTF();
	            
	            System.out.println(msg);
	            
	            DataOutputStream out = new DataOutputStream(pSocket.getOutputStream());
	            
	            String method = getMethod(msg);
	            
	            if (!method.equals("LIST")) msg = msg.substring(msg.indexOf(' ')+1);
	            
	            if (method.equals("LOOKUP")) {
	            	if (getRFC(msg) == null) out.writeUTF("RFC not found\n");	            
	            }
	            else if (method.equals("ADD")) {
	            	addRFC(pSocket.getRemoteSocketAddress().toString(),msg);
	            	out.writeUTF("RFC added\n");
	            }
	            else if (method.equals("LIST")) {
	            	out.writeUTF("RFCs Available:\n" + getList());
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
		if (msg.equals("LIST")) return msg;
		return msg.substring(0, msg.indexOf(' '));
	}
	
	public void addRFC(String peer, String msg) {
		char parser = '\n';
		
		String rfc = msg.substring(0, msg.indexOf(parser));
		msg = msg.substring(msg.indexOf(parser) + 1);
		String port = msg;//.substring(0, msg.indexOf(parser));
		
		peerList[arrayCount] = peer;
		rfcList[arrayCount] = rfc;
		portList[arrayCount] = Integer.parseInt(port);
		
		arrayCount++;
	}
	
	public String getRFC(String msg) {
		for (int i=0; i<arrayCount;i++) {
			if (rfcList[i].equals(msg)) {
				String ret = peerList[i] + "\n" + portList[i]; 
				return ret;
			}
		}
		return null;
	}
	
	public String getList() {
		String list = "";
		for (int i=0; i<arrayCount;i++) {
			list += peerList[i] + " " + rfcList[i] + " " + portList[i] + "\n";
		}
		
		if (list.length() < 1) return "List is empty.\n";
		else return list;
	}

	public static void main(String[] args) {
		Server server = new Server();
		
		server.run();
	}

}
