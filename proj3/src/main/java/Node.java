
import java.util.HashSet;
import org.xml.sax.Attributes;

/**
 * Created by Jesse on 4/16/2016.
 */
public class Node {
    private String nodeID;
    private double latitude;
    private HashSet<Node> adjacentNodes;

    private double longitude;
    private Attributes attributes;
    public Node(Attributes attributes) {
        this.attributes = attributes;
        nodeID = attributes.getValue("id");
        latitude = Double.parseDouble(attributes.getValue("lat"));
        longitude = Double.parseDouble(attributes.getValue("lon"));
        adjacentNodes = new HashSet<Node>();
    }

    public String getNodeID() {
        return nodeID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void connect(Node node) {
        adjacentNodes.add(node);
    }

    public boolean hasNoAdjacentNodes() {
        return adjacentNodes.isEmpty();
    }

    // not actual distance, only for comparing
    public double distanceTo(Node node) {
        return Math.abs(node.getLatitude() - latitude) + Math.abs(node.getLongitude() - longitude);
    }
}
