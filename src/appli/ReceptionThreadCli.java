/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */


import java.io.*;
import java.net.*;

public class ReceptionThreadCli
	extends Thread {
	
	Socket clientSocket;
	//ServerSocket listenSocket = null;
	//int selfPort;

	
	ReceptionThreadCli(Socket c){
		//selfPort = p;
		clientSocket = c;
	}


 	/**	
  	*
  	**/
	public void run() {
    	  try {
			System.out.println("Ouverture du thread d'écoute sur le port : " + clientSocket.getLocalPort());
			// Création du listener de socket
            //listenSocket = new ServerSocket(selfPort); //port

			
    		//PrintStream socOut = new PrintStream(clientSocket.getOutputStream());

			while(true)
			{
				// On accepte une demande de connexion d'une socket et on créer un thread d'écoute par client
				//clientSocket = listenSocket.accept();
				// Gestion du buffer
				
				BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));    
				if(clientSocket.getInetAddress() != null)
				{
					System.out.println("Message received from:" + clientSocket.getInetAddress());
					System.out.println("ReceptionThreadCli received : " + socIn.readLine());
				}
				
			}


    	} catch (Exception e) {
        	System.err.println("Error in ReceptionThreadCli:" + e); 
		}
       }
  
  }

  
