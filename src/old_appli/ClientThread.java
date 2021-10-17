/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread
	extends Thread {
	
	private Socket clientSocket;

	ArrayList<ArrayList<String>> PersonnalMessages = new ArrayList<ArrayList<String>>(); 
	String pseudo;
	
	ClientThread(Socket s) {
		this.clientSocket = s;
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
    	  try {
			


    		BufferedReader socIn = null;
    		socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			String line = socIn.readLine();
			this.pseudo = line;
			System.out.println(line + " just arrived");

    		while (true) {
    		  line = socIn.readLine();
    		  socOut.println(line);

			  System.out.println(this.pseudo + " said " + line);
    		}
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
       }
  
  }

  
