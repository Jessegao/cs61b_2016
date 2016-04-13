import java.awt.image.BufferedImage;

/**
 * Created by Jesse on 4/13/2016.
 */
public class QuadTreeNode {
    private BufferedImage tile;
    private QuadTreeNode upperRight;
    private QuadTreeNode upperLeft;
    private QuadTreeNode lowerRight;
    private QuadTreeNode lowerLeft;
    //this will be the number that shows where the tile of this quadrant lies
    private int referenceLevel;

    public QuadTreeNode(int referenceLevel) {
        this.referenceLevel = referenceLevel;
    }

    public BufferedImage getTile() {
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
        return quadrant + 10 * referenceLevel;
    }
}
