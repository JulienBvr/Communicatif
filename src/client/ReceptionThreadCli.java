package client;

/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */


import IHM.*;
import java.io.*;
import java.net.*;

public class ReceptionThreadCli
	extends Thread {
	
	Socket clientSocket;
        Accueil window;
	//ServerSocket listenSocket = null;
	//int selfPort;

	
	ReceptionThreadCli(Socket c){
		//selfPort = p;
		this.clientSocket = c;
	}

        public void setWindow(Accueil a)
        {
            this.window = a;
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
                                        String[] reception = socIn.readLine().split(":", 2);
                                        
					System.out.println("Message received from:" + reception[0]);
					System.out.println("ReceptionThreadCli received : " + reception[1]);
                                        
                                        //Affichage sur l'interface
                                        
                                        
                                        System.out.println("Debut du test");
                                        
                                        if(reception.length == 2)
                                        {
                                            System.out.println("sending message to window");
                                            String sender = reception[0];
                                            String msg = reception[1];
                                            System.out.println("INIT sender : " + sender);
                                            System.out.println("INIT msg : " + msg);
                                            this.window.recepMessage(sender, msg);
                                            System.out.println("message sended to window");
                                        }
                                        else
                                        {
                                            System.out.println("ERROR message reçus non conforme");
                                        }
                                        System.out.println("Fin du test");
				}
				
			}


    	} catch (Exception e) {
        	System.err.println("Error in ReceptionThreadCli:" + e); 
		}
       }
  
  }

  
