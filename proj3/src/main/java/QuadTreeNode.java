import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Jesse on 4/13/2016.
 */
public class QuadTreeNode {

    // The deepest the tree can go
    private static final int DEEPEST_DEPTH = 7;

    private int tile;
    private QuadTreeNode upperRight;
    private QuadTreeNode upperLeft;
    private QuadTreeNode lowerRight;
    private QuadTreeNode lowerLeft;

    private int depth;
    private double upperLeftLatitude;
    private double upperLeftLongitude;
    private double lowerRightLatitude;
    private double lowerRightLongitude;

    public QuadTreeNode(int depth, double upperLeftLatitude, double upperLeftLongitude, double lowerRightLatitude, double lowerRightLongitude, int fileName) {
        this.depth = depth;
        this.upperLeftLatitude = upperLeftLatitude;
        this.upperLeftLongitude = upperLeftLongitude;
        this.lowerRightLatitude = lowerRightLatitude;
        this.lowerRightLongitude = lowerRightLongitude;
        tile = fileName;

        // Instantiate to the Deepest Depth
        if (depth < DEEPEST_DEPTH) {
            double halfLat = (upperLeftLatitude + lowerRightLatitude) / 2;
            double halfLong = (upperLeftLongitude + lowerRightLongitude) / 2;
            upperLeft = new QuadTreeNode(depth + 1, upperLeftLatitude, upperLeftLongitude, halfLat, halfLong, tile * 10 + 1);
            upperRight = new QuadTreeNode(depth + 1, upperLeftLatitude, halfLong, halfLat, lowerRightLongitude, tile * 10 + 2);
            lowerLeft = new QuadTreeNode(depth + 1, halfLat, upperLeftLongitude, lowerRightLatitude, halfLong, tile * 10 + 3);;
            lowerRight = new QuadTreeNode(depth + 1, halfLat, halfLong, lowerRightLatitude, lowerRightLongitude, tile * 10 + 4);;
        }
    }

    public String toString() {
        String children = "The children are:\n" + upperLeft + upperRight + lowerLeft + lowerRight + "\n";
        return ("depth = " + depth + "\n" + "upperleftLatitude = " + upperLeftLatitude + "\n" + "upperLeftLongitude = " + upperLeftLongitude
                + "\n" + "lowerRightLatitude = " + lowerRightLatitude + "\n" + "lowerRightLongitude = " + lowerRightLongitude  + "\n" + "tile = " + tile + "\n \n"
                + children);
    }

    public String getPictureName() {
        if (tile == 0) {
            return "root";
        }
        return String.valueOf(tile);
    }

    public QuadTreeNode getUpperRight() {
        return upperRight;
    }

    public QuadTreeNode getUpperLeft() {
        return upperLeft;
    }

    public QuadTreeNode getLowerRight() {
        return lowerRight;
    }

    public QuadTreeNode getLowerLeft() {
        return lowerLeft;
    }

    //returns a number corresponding to the proper image and its location
    private int getPictureNumber(int quadrant) {
        return quadrant + 10 * depth;
    }

}
