

import java.io.*;
import java.net.*;
//import java.util.*;



public class AccountClient {

 
  /**
  *  main method
  *  accepts a connection, receives a message from client then sends an echo to the client
  **/
    public static void main(String[] args) throws IOException {

        Socket serverSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;
        ServerSocket listenSocket = null;
        String pseudo = new String();

        int hostPort;

        if (args.length != 2) {
          System.out.println("Usage: java EchoClient <reception port>  <pseudo>");
          System.exit(1);
        }

        try {
      	    // creation socket ==> connexion au serveur de messagerie
      	    serverSocket = new Socket("127.0.0.1", 8080);
	          socIn = new BufferedReader(
	    		            new InputStreamReader(serverSocket.getInputStream()));    
	          socOut= new PrintStream(serverSocket.getOutputStream());
	          stdIn = new BufferedReader(new InputStreamReader(System.in));

            // Creating the reception Thread and a receptionsocket in it
            hostPort = Integer.parseInt(args[0]);
            listenSocket = new ServerSocket(hostPort); //port
            Socket personnalSocket = listenSocket.accept(); // Reads the socket from the port
            System.out.println("Receiver socket activated on the port: " + personnalSocket.getInetAddress());
            ReceiverThread rt = new ReceiverThread(personnalSocket); // Creates a new reception thread
            rt.start();
                        
            // initialisation
            pseudo = args[0];
            socOut.println(pseudo);
            socOut.println(hostPort);
            System.out.println("Welcome "+ pseudo);        


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[1]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ args[1]);
            System.exit(1);
        }
                             
        String line;

        while (true) {
        	line=stdIn.readLine();
        	if (line.equals(".")) break;
        	socOut.println(line);
        	System.out.println("echo: " + socIn.readLine());
        }

      socOut.close();
      socIn.close();
      stdIn.close();
      serverSocket.close();
      listenSocket.close();

    }
}


