package server;

/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */


import java.io.*;
import java.net.*;

public class SenderThread
	extends Thread {
	
	Socket senderSocket = null;
	PrintStream socOut = null;
	BufferedReader stdIn = null;
	BufferedReader socIn = null;
	String destinationAdress;
	Socket reusedSocket;
	String destinationMessage;
	ConnexionsTable connexionsTable;
        String senderPseudo;
        String destinationGroup;
        String destinationPseudo;
	

	SenderThread(String m, String da, Socket s, String sp, String dg, String dp) {
		this.destinationMessage = m;
		this.destinationAdress = da;
		this.reusedSocket = s; 
                this.senderPseudo = sp;
                this.destinationGroup = dg;
                this.destinationPseudo = dp;
		System.out.println("SenderThread# destinationMessage : "+ destinationMessage);
	}

 	/**	
  	* receives a request from client then sends an echo to the client
  	**/
        @Override
	public void run() {
    	try {
			// creation socket ==> connexion
			socIn = new BufferedReader(
						new InputStreamReader(reusedSocket.getInputStream()));    
			socOut= new PrintStream(reusedSocket.getOutputStream());

			// Envoi du message
                        String sender = this.senderPseudo;
                        if(this.destinationGroup.equals(this.destinationPseudo)){} 
                        else 
                        {
                            sender = this.senderPseudo + " pour " + this.destinationGroup;
                        }
			socOut.println(sender + ":" + destinationMessage);
			System.out.println("Message " + destinationMessage + " from "+ senderPseudo +" sended to " + reusedSocket.getPort());
			
    		
    	} catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + destinationAdress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ destinationAdress);
            System.exit(1);
        }
	}
  
  }

  
