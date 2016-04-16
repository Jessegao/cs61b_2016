import java.util.HashMap;

/**
 * Created by Jesse on 4/16/2016.
 */
public class Node {
    private HashMap<String, String> tags;
    private int nodeID;
    private double latitude;
    private double longitude;

    public Node(HashMap<String, String> tags, int nodeID, double latitude, double longitude) {
        this.tags = tags;
        this.nodeID = nodeID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public int getNodeID() {
        return nodeID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
