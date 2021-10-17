/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
import java.io.*;
import java.net.*;

public class Client {

 
/**
*  main method
*  accepts a connection, receives a message from client then sends an echo to the client
**/
    public static void main(String[] args) throws IOException {

        Socket clientSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;

        String serverHost;
        serverHost = args[0];
        if (args.length != 3) {
          System.out.println("Usage: java EchoClient <Server host> <Server port> <Client pseudo>");
          System.exit(1);
        }

        try {

            
      	    // creation socket ==> connexion
      	    clientSocket = new Socket(serverHost, Integer.parseInt(args[1]));
	          socIn = new BufferedReader(
	    		          new InputStreamReader(clientSocket.getInputStream()));    
	          socOut= new PrintStream(clientSocket.getOutputStream());
	          stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Ouverture de la socket sur le port : " + clientSocket.getLocalPort());

            // Communication du Pseudo au serveur
            socOut.println(args[2]);
            // Lancement du thread d'écoute
            ReceptionThreadCli rt = new ReceptionThreadCli(clientSocket);
            rt.start();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ args[0]);
            System.exit(1);
        }
        
        String line;
        while (true) {
        	line=stdIn.readLine();
        	if (line.equals(".")) break;
        	socOut.println(serverHost+":"+line);
        	System.out.println("echo: " + line);
        }

      socOut.close();
      socIn.close();
      stdIn.close();
      clientSocket.close();
    }
}


