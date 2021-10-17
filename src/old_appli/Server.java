/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */


//import java.io.*;
import java.net.*;

public class Server  {
  
 	/**
  	* main method
	* @param EchoServer port
	* 
	* Only handle new connections
  	* 
  	**/
        public static void main(String args[]){ 
        ServerSocket listenSocket;
		ServerSocket saySocket;

		public static ArrayList<int> clientsPort = new ArrayList<int>;
        
  	if (args.length != 0) {
          System.out.println("Usage: java EchoServer");
          System.exit(1);
  	}
	try {
		listenSocket = new ServerSocket(8080); //port
		System.out.println("Server ready..."); 

		//Initialise receiver thread
		/*
		Socket receiveSocket = listenSocket.accept(); // Reads the socket from the port
		System.out.println("Receiver socket activated on the port: " + receiveSocket.getInetAddress());
		ReceiverThread rt = new ReceiverThread(receiveSocket); // Creates a new reception thread
		rt.start();
		*/

		while (true) {
			Socket receiveSocket = listenSocket.accept(); // Reads the socket from the port
			System.out.println("Receiver socket activated on the port: " + receiveSocket.getInetAddress());
			ReceiverThread rt = new ReceiverThread(receiveSocket); // Creates a new reception thread
			rt.start();

			saySocket = new ServerSocket(client); //port
			Socket senderSocket = saySocket.accept(); // Reads the socket from the port
			System.out.println("Sender socket activated on the port: " + senderSocket.getInetAddress());
			SenderThread st = new SenderThread(senderSocket); // Creates a new reception thread
			st.start();
		}
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
      }
  }

  