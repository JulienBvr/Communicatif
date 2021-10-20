package server;

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
	String destinationGroup;
        String destinationPseudo;
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
  	**/
        @Override
	public void run() {
    	  try 
          {
            // Gestion du buffer
            BufferedReader socIn = null;
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    // Référencement de la correspondance entre nom de communication et ports.
                    selfPseudo = socIn.readLine();
                    System.out.println("ReceptionThreadServ # pseudo : "+ selfPseudo + " on the port : " + clientSocket.getPort());
                    // Création du lien entre le pseudo et sa socket
                    connexionsTable.addSocket(selfPseudo, clientSocket);
                    // Création de la conversation avec lui-même
                    connexionsTable.addPseudo(selfPseudo, selfPseudo);

                    // Ecoute des messages entrant
            while (true) 
            {
                String message = socIn.readLine();
                System.out.println("ReceptionThreadServ received : "+ message);

                if(message != null)
                {
                    // Traitement du message
                    String[] trame  = message.split(":");
                    // Envoi d'un message à un groupe / personne
                    if(trame.length == 3 && !(trame[1].equals("")))
                    {

                        destinationAdress = trame[0];
                        destinationMessage = trame[2];
                        System.out.println("ReceptionThreadServ# destinationMessage : " + destinationMessage);
                        destinationGroup = trame[1];

                        /*
                        if(!this.connexionsTable.getSocketTable().containsKey(trame[1]))
                        {
                            for(String iTpseudo : connexionsTable.getPseudos(destinationGroup))
                            {
                                this.discussions.putMessage(iTpseudo, selfPseudo, destinationMessage);
                            }
                            destinationMessage = "Cet utilisateur n'existe pas ou n'est pas connecté";
                            destinationGroup = this.selfPseudo;
                            System.out.println("Archivage d'un message");
                        }*/
                        for(String iTpseudo : connexionsTable.getPseudos(destinationGroup))
                        {
                            Socket socket;
                            // Persistance des messages pour chaque client
                            this.discussions.putMessage(iTpseudo, destinationGroup, selfPseudo, destinationMessage);
                            
                            // Si le client est connecté
                            if(connexionsTable.getSocketTable().containsKey(iTpseudo))
                            {
                                socket = connexionsTable.getSocket(iTpseudo);
                                // Eviter de s'envoyer le message à soi même lors de l'écriture à un groupe
                                if(connexionsTable.getSocket(selfPseudo) != socket)
                                {
                                    // Création d'un thread d'envoi par message et par destination
                                    SenderThread st = new SenderThread(destinationMessage, destinationAdress, socket, this.selfPseudo, destinationGroup, iTpseudo);
                                    st.start();
                                }
                            } else
                            {
                                System.out.println("Archivage du message "+ destinationMessage+" provenant de "+iTpseudo+" pour le groupe "+destinationGroup);
                                // Envoie d'un message d'erreur
                                destinationMessage = "L'utilisateur "+iTpseudo+" n'est pas connecte";
                                socket = connexionsTable.getSocket(this.selfPseudo);
                                
                                SenderThread st = new SenderThread(destinationMessage, destinationAdress, socket, iTpseudo, this.selfPseudo, this.selfPseudo);
                                st.start();
                            }
                            
                        }
                    }

                    // Abonnement à un groupe
                    if(trame.length == 2 && !(trame[1].equals("")))
                    {
                            destinationAdress = trame[0];
                            String groupToJoin = trame[1];
                            // Créer si innexistant
                            if(!connexionsTable.getGroupTable().containsKey(groupToJoin))
                            {
                                    connexionsTable.addPseudo(groupToJoin, this.selfPseudo);
                                    System.out.println("Groupe cree : " + groupToJoin);
                                    System.out.println(this.selfPseudo + " a joint " + groupToJoin);
                            }
                            else
                            {
                                // Connecter si pas encore connecté
                                if(!connexionsTable.getGroupTable().get(groupToJoin).contains(this.selfPseudo))
                                {
                                    connexionsTable.addPseudo(groupToJoin, selfPseudo);
                                    System.out.println(this.selfPseudo + " a joint " + groupToJoin);
                                }else{
                                    System.out.println(selfPseudo + " est deja dans le groupe " + groupToJoin);
                                }
                            }	
                    }

                    // Récupération des messages manqués
                    if(trame.length == 1)
                    {
                        if(trame[0].equals("exit"))
                        {
                            connexionsTable.removeSocket(this.selfPseudo);
                        } else 
                        {
                            destinationAdress = trame[0];
                            if(this.discussions.getDiscussions().containsKey(this.selfPseudo))
                            {
                                for(String fullMsg : this.discussions.getDiscuss(this.selfPseudo))
                                {
                                    String[] fullMsgSplitted = fullMsg.split(":");
                                    String senderPseudo = fullMsgSplitted[0];
                                    String groupName = fullMsgSplitted[1];
                                    String msg = fullMsgSplitted[2];

                                    Socket socket = connexionsTable.getSocket(this.selfPseudo);

                                    // Création d'un thread d'envoi par message et par destination
                                    SenderThread st = new SenderThread(msg, destinationAdress, socket, senderPseudo, groupName, selfPseudo);
                                    st.start();
                                }
                            } 
                        }
                    }
                }else{
                    connexionsTable.removeSocket(this.selfPseudo);
                    System.out.println("Client disconnected");
                    break;
                }
            }
    	} catch (IOException e) {
        	System.err.println("Error in ReceptionThreadServ : " + e); 
        }
       }
  
  }

  
