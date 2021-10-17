import java.util.*;

public class Discussions {
  
    protected Hashtable<String, ArrayList<String>> discuslist = new Hashtable<String, ArrayList<String>>();
    Discussions(){}

    public ArrayList<String> getDiscuss(String groupName){return this.discuslist.get(groupName);}


    public void putMessage(String discussOwner, String author, String message)
    {
        ArrayList<String> msg = new ArrayList<String>();
        if(discuslist.containsKey(discussOwner))
        {
            msg = discuslist.get(discussOwner);
            discuslist.remove(discussOwner);
        }
        msg.add(author + ":"  + message);
        discuslist.put(discussOwner, msg);
    }    
}
