/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.io.*;
import java.net.*;

public class ReceiverThread
    extends Thread 
    {

      private Socket receiveSocket;      
      ReceiverThread(Socket s) 
      {
        this.receiveSocket = s;
      }

      public void run()
      {
        try 
        {
          BufferedReader socIn = null;
          socIn = new BufferedReader(
                  new InputStreamReader(receiveSocket.getInputStream()));    
          PrintStream socOut = new PrintStream(receiveSocket.getOutputStream());

          String line = socIn.readLine();
          System.out.println(line + " just arrived");
          
          while (true) 
          {
            line = socIn.readLine();
            socOut.println(line);
            System.out.println("echo : " + line);
            
          }

        } 
        catch (Exception e) 
        {
          System.err.println("Error in EchoServer:" + e); 
        }
      }
    }
