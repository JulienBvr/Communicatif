/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

//import java.io.*;
import java.net.*;
//import java.util.*;

public class Server  {
  
 	/**
  	* main method
	* @param Server port
  	* 
  	**/
	public static void main(String args[]){ 
    	ServerSocket listenSocket;
		ConnexionsTable connexionsTable = new ConnexionsTable();
		Discussions discussions = new Discussions();
        
  	if (args.length != 1) {
          System.out.println("Usage: java Server <Server port>");
          System.exit(1);
  	}
	try {

			// Création de la socket d'écoute sur le serveur sur un port unique
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port

			
			System.out.println("Server ready..."); 

			while (true) {
				// On accepte une demande de connexion d'une socket et on créer un thread d'écoute par client
				Socket clientSocket = listenSocket.accept();
				ReceptionThreadServ rt = new ReceptionThreadServ(clientSocket, connexionsTable, discussions);
				rt.start();
				System.out.println("Connexion from:" + clientSocket.getInetAddress());

			}
        } catch (Exception e) {
            System.err.println("Error in Server:" + e);
        }
      }
  }

  