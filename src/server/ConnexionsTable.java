package server;

import java.util.*;
import java.net.*;

public class ConnexionsTable {
    /**
    * Une table fait le lien entre un pseudo et les clients liés à ce pseudo (pour un groupe par exemple)
    * Une autre table fait le lien entre le client et sa socket initialisée à la connexion
    *
    **/

    protected Hashtable<String, Socket> socketTable = new Hashtable<String, Socket>();
    protected Hashtable<String, ArrayList<String>> groupTable = new Hashtable<String, ArrayList<String>>();

    ConnexionsTable(){}

    public Hashtable<String, Socket> getSocketTable(){return this.socketTable;}
    public Hashtable<String, ArrayList<String>> getGroupTable(){return this.groupTable;}
    
    public void addSocket(String pseudo, Socket clientSocket){
        try{
            socketTable.put(pseudo, clientSocket);
        }
        catch(NullPointerException e)
        {
            System.out.println("Error in ConnexionTable : " + e);   
        }
    }
    
    public ArrayList<Socket> getClientsSockets(String grpPseudo){
        ArrayList<Socket> sockets = new ArrayList<Socket>();
            for(String pseudo : groupTable.get(grpPseudo))
            {
                sockets.add(socketTable.get(pseudo));
            }
            return sockets;
    }
    
    public Socket getSocket(String pseudo){
            return socketTable.get(pseudo);
    }
    
    public void removeSocket(String pseudo)
    {
        socketTable.remove(pseudo);
    }
    
    public ArrayList<String> getPseudos(String pseudo)
    {
        ArrayList<String> ps = new ArrayList<>();
            for(String pso : groupTable.get(pseudo))
            {
                ps.add(pso);
            }
            return ps;
    }

    public void addPseudo(String groupeName, String pseudo)
    {
        // Ajoute le pseudo à la conversation si elle existe déjà, sinon créer la conversation et ajoute son propre pseudo
        // Chaque personne entame une conversation avec elle même
        ArrayList<String> pseudos = new ArrayList<String>();
        if(groupTable.containsKey(groupeName)){
            pseudos = groupTable.get(groupeName);
            groupTable.remove(groupeName);
        }
        pseudos.add(pseudo);
        groupTable.put(groupeName, pseudos);
    }

    public void removePseudo(String groupeName, String pseudo)
    {
        ArrayList<String> pseudos = groupTable.get(groupeName);
        groupTable.remove(groupeName);
        pseudos.remove(pseudo);
        groupTable.put(groupeName, pseudos);
    }
    
    
}
