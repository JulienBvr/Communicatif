package client;

/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
import IHM.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    protected String selfPseudo;
    protected String serverHost;
    protected int servPort;
    
    Socket clientSocket = null;
    PrintStream socOut = null;
    BufferedReader stdIn = null;
    BufferedReader socIn = null;
    
    ReceptionThreadCli rt;
    Accueil window;

    
    
    public Client(String n, String a, int p)
    {
        this.selfPseudo = n;
        this.serverHost = a;
        this.servPort = p;
        
        initialize();
    }
/**
*  main method
*  accepts a connection, receives a message from client then sends an echo to the client
**/
    
    private void initialize()
    {
        try {
      	    // creation socket ==> connexion
      	    this.clientSocket = new Socket(this.serverHost, this.servPort);
	          this.socIn = new BufferedReader(
	    		          new InputStreamReader(this.clientSocket.getInputStream()));    
	          this.socOut= new PrintStream(this.clientSocket.getOutputStream());
	          this.stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Ouverture de la socket sur le port : " + this.clientSocket.getLocalPort());

            // Communication du Pseudo au serveur
            this.socOut.println(this.selfPseudo);
            // Lancement du thread d'écoute
            this.rt = new ReceptionThreadCli(this.clientSocket);
            rt.start();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + this.serverHost);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ this.serverHost);
            System.exit(1);
        }
    }
    
    public ReceptionThreadCli getReceptionThread()
    {
        return this.rt;
    }
    
    public String getName(){return this.selfPseudo;}
            
    
    public void setAccueilWind(Accueil a)
    {
        this.window = a;
        this.rt.setWindow(this.window);
    }
    
    public void send(String message, String destination)
    {
        if(!message.equals(""))
        {
            socOut.println(serverHost+":"+destination+":"+message);
            System.out.println("Sended -- "+destination+":"+ message);
        }
        else
        {
            socOut.println(serverHost+":"+destination);
            System.out.println("Sended -- "+destination);
        }
        
    }
    
    public void sendInitRequest()
    {
        socOut.println("init");  
    }
        
    
   
    public void quit() throws IOException
    {
        socOut.println("exit");
        System.out.println("Fermeture de la socket");
        socOut.close();
        clientSocket.close();
        
    }
}

