/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.io.*;
import java.net.*;

public class SenderThread
    extends Thread 
    {

      private Socket senderSocket;
      PrintStream socOut = null;
      BufferedReader stdIn = null;
      BufferedReader socIn = null;

      int destinationPort;
      String adress;
      String message;
      SenderThread(Socket s) 
      {
        this.senderSocket = s;
      }

      public void run()
      {
        try 
        {

          // creation socket ==> connexion au client
          senderSocket = new Socket(adress, destinationPort);
          BufferedReader socIn = new BufferedReader(new InputStreamReader(senderSocket.getInputStream()));
          PrintStream socOut = new PrintStream(senderSocket.getOutputStream());
          

        } catch (UnknownHostException e) {
          System.err.println("Don't know about host:" + adress);
          System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                              + "the connection to:"+ adress);
            System.exit(1);
        }

        while (true) 
        {
          // Envoyer un message via la socket
          socOut.println(message);

          System.out.println("echo: " + message);
          
        }
      }
    }
