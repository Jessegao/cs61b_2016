import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Jesse on 4/13/2016.
 */
public class QuadTreeNode {

    // The deepest the tree can go
    private static final int DEEPEST_DEPTH = 7;

    private File tile;
    private QuadTreeNode upperRight;
    private QuadTreeNode upperLeft;
    private QuadTreeNode lowerRight;
    private QuadTreeNode lowerLeft;

    private int depth;
    private double upperLeftLatitude;
    private double upperLeftLongitude;
    private double lowerRightLatitude;
    private double lowerRightLongitude;

    public QuadTreeNode(int depth, double upperLeftLatitude, double upperLeftLongitude, double lowerRightLatitude, double lowerRightLongitude, File file) {
        this.depth = depth;
        this.upperLeftLatitude = upperLeftLatitude;
        this.upperLeftLongitude = upperLeftLongitude;
        this.lowerRightLatitude = lowerRightLatitude;
        this.lowerRightLongitude = lowerRightLongitude;
        tile = file;

        if (depth < DEEPEST_DEPTH) {
            TODO instantiate upperRight ...
        }
    }

    public File getTile() {
        return tile;
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
