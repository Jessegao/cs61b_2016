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
            upperLeft = new QuadTreeNode(depth + 1, upperLeftLatitude, upperLeftLongitude, lowerRightLatitude / 2.0, lowerRightLongitude / 2.0, tile * 10 + 1);
            upperRight = new QuadTreeNode(depth + 1, upperLeftLatitude, upperLeftLongitude / 2.0, lowerRightLatitude / 2.0, lowerRightLongitude, tile * 10 + 2);
            lowerLeft = new QuadTreeNode(depth + 1, upperLeftLatitude / 2.0, upperLeftLongitude, lowerRightLatitude, lowerRightLongitude / 2.0, tile * 10 + 3);;
            lowerRight = new QuadTreeNode(depth + 1, upperLeftLatitude, upperLeftLongitude / 2.0, lowerRightLatitude, lowerRightLongitude / 2.0, tile * 10 + 4);;
        }
    }

    public String toString() {
        return ("depth = " + depth + "\n" + "upperleftLatitude = " + upperLeftLatitude + "depth = " + depth + "depth = " + depth)
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
