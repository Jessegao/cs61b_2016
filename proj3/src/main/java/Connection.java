import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by Jesse on 4/16/2016.
 */
public class Connection {
    private int connectionID;
    private Node node1;
    private Node node2;
    private HashMap<String, String> tags;

    public Connection(int connectionID, Node node1, Node node2, HashMap<String, String> tags) {
        this.connectionID = connectionID;
        this.node1 = node1;
        this.node2 = node2;
        this.tags = tags;
    }

    public int getConnectionID() {
        return connectionID;
    }



    public HashMap<String, String> getTags() {
        return tags;
    }
}
