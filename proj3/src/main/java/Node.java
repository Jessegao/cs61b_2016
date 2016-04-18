
import java.util.HashSet;

import org.xml.sax.Attributes;

/**
 * Created by Jesse on 4/16/2016.
 */
public class Node implements Comparable<Node> {
    private String nodeID;
    private double latitude;
    private HashSet<Node> adjacentNodes;

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    private Node previousNode;
    //private Node endNode;
    private double distanceFromStart = Double.MAX_VALUE;
    private double distanceToEnd = Double.MAX_VALUE;

    public void setDistanceFromStart(double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public void setDistanceToEnd(double distanceToEnd) {
        this.distanceToEnd = distanceToEnd;
    }

    public double getDistanceFromStart() {
        return distanceFromStart;
    }

    public double getDistanceToEnd() {
        return distanceToEnd;
    }

    private double longitude;
    private Attributes attributes;

    public Node(Attributes attributes) {
        this.attributes = attributes;
        nodeID = attributes.getValue("id");
        latitude = Double.parseDouble(attributes.getValue("lat"));
        longitude = Double.parseDouble(attributes.getValue("lon"));
        adjacentNodes = new HashSet<Node>();
    }

    public HashSet<Node> getAdjacentNodes() {
        return adjacentNodes;
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

    public double distanceTo(Node node) {
        double londif = longitude - node.getLongitude();
        double laddif = latitude - node.getLatitude();
        return Math.sqrt(londif * londif + laddif * laddif);
    }

    public int compareTo(Node node) {
        double difference = distanceFromStart + distanceToEnd
                - (node.distanceFromStart + node.distanceToEnd);
        if (difference < 0) {
            return -1;
        } else if (difference > 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
