import java.util.*;
import java.net.*;

public class ConnexionsTable {
    /**
    * Une table fait le lien entre un pseudo et les clients liés à ce pseudo (pour un groupe par exemple)
    * Une autre table fait le lien entre le client et sa socket initialisée à la connexion
    *
    **/

    protected Hashtable<String, Socket> socketTable = new Hashtable<String, Socket>();
    protected Hashtable<String, ArrayList<String>> pseudoTable = new Hashtable<String, ArrayList<String>>();

    ConnexionsTable(){}

    public Hashtable<String, Socket> getSocketTable(){return this.socketTable;}
    public Hashtable<String, ArrayList<String>> getPseudoTable(){return this.pseudoTable;}
    
    public void addSocket(String pseudo, Socket clientSocket){
        try{
            socketTable.put(pseudo, clientSocket);
        }
        catch(NullPointerException e)
        {
            System.out.println("Error in ConnexionTable : " + e);   
        }
    }

    public void addPseudo(String groupeName, String pseudo)
    {
        ArrayList<String> pseudos = new ArrayList<String>();
        if(pseudoTable.containsKey(groupeName)){
            pseudos = pseudoTable.get(groupeName);
            pseudoTable.remove(groupeName);
        }
        pseudos.add(pseudo);
        pseudoTable.put(groupeName, pseudos);
    }

    public void removePseudo(String groupeName, String pseudo)
    {
        ArrayList<String> pseudos = pseudoTable.get(groupeName);
        pseudoTable.remove(groupeName);
        pseudos.remove(pseudo);
        pseudoTable.put(groupeName, pseudos);
    }
    
    public ArrayList<Socket> getClientSocket(String grpPseudo){
        ArrayList<Socket> sockets = new ArrayList<Socket>();
            for(String pseudo : pseudoTable.get(grpPseudo))
            {
                sockets.add(socketTable.get(pseudo));
            }
            return sockets;
    }

    public void createGroup(String groupeName)
    {
        ArrayList<String> pseudos = new ArrayList<String>();
        if(!pseudoTable.containsKey(groupeName)){
            pseudoTable.put(groupeName, pseudos);
        }
    }
}
