/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */


import java.io.*;
import java.net.*;
import java.util.*;

public class ReceptionThreadServ
	extends Thread {
	
	private Socket clientSocket;

	String destinationAdress;
	String destinationPseudos;
	ArrayList<Integer> listDestinations = new ArrayList<Integer>();
	String destinationMessage;
	ConnexionsTable connexionsTable;
	Discussions discussions;
	String selfPseudo;
	

	ReceptionThreadServ(Socket s, ConnexionsTable ct, Discussions d) {
		this.clientSocket = s;
		this.connexionsTable = ct;
		this.discussions = d;
	}

 	/**	
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
    	  try {

			// Gestion du buffer
    		BufferedReader socIn = null;
    		socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// Référencement de la correspondance entre nom de communication et ports.
			selfPseudo = socIn.readLine();
			System.out.println("ReceptionThreadServ # pseudo : "+ selfPseudo + " on the port : " + clientSocket.getPort());
			connexionsTable.addSocket(selfPseudo, clientSocket);
			connexionsTable.addPseudo(selfPseudo, selfPseudo);

			// Ecoute des messages entrant
    		while (true) {
				String message = socIn.readLine();
				System.out.println("ReceptionThreadServ received : "+ message);

				// Traitement du message
				String[] trame = message.split(":");
				// Envoi d'un message à un groupe / personne
				if(trame.length == 3)
				{
					destinationAdress = trame[0];
					destinationMessage = trame[2];
					System.out.println("ReceptionThreadServ# destinationMessage : "+ destinationMessage);
					destinationPseudos = trame[1];
					for(Socket socket : connexionsTable.getClientSocket(destinationPseudos))
					{
						// Création d'un thread d'envoi par message et par destination
						SenderThread st = new SenderThread(destinationMessage, destinationAdress, socket);
						st.start();
					}


					// TODO : persistance des messages
					this.discussions.putMessage(destinationPseudos, selfPseudo, destinationMessage);

				}

				// Abonnement à un groupe
				if(trame.length == 2)
				{
					destinationAdress = trame[0];
					String groupToJoin = trame[1];
					// Créer si innexistant
					if(!connexionsTable.getPseudoTable().containsKey(groupToJoin))
					{
						connexionsTable.createGroup(groupToJoin);
					}
					connexionsTable.addPseudo(groupToJoin, selfPseudo);
				}
								

				
    		}
    	} catch (Exception e) {
        	System.err.println("Error in ReceptionThreadServ :" + e); 
        }
       }
  
  }

  
