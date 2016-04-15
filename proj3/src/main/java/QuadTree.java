import java.io.File;
import java.util.TreeSet;

/**
 * Created by Jesse on 4/13/2016.
 */
public class QuadTree {
    private QuadTreeNode root;
    private static final int DEPTH_ROOT = 0;

    public QuadTree(double ROOT_ULLAT, double ROOT_ULLON, double ROOT_LRLAT, double ROOT_LRLON, int rootFile) {
        root = new QuadTreeNode(DEPTH_ROOT, ROOT_ULLAT, ROOT_ULLON, ROOT_LRLAT, ROOT_LRLON, rootFile);
    }

    public String toString() {
        return root.toString();
    }

    public TreeSet<String> getRasterImages(Double top, Double left, Double bottom, Double right, Double width, Double height) {
        TreeSet<String> imageNames = new TreeSet<String>();
        Rectangle query = new Rectangle(left, right, top, bottom);
        double dpp = Math.abs(left - right) / width;
        recursiveDestructiveCollect(imageNames, root, dpp, query);
        return imageNames;
    }

    /** Recursively gets all the tiles' names (no root/ or .png added yet) and puts them into imageNames */
    private void recursiveDestructiveCollect(TreeSet<String> imageNames, QuadTreeNode node, double dpp, Rectangle query) {
        if (isDeepEnough(node, dpp)) { // inefficient
            imageNames.add(node.getPictureName());
        } else {
            if (query.intersects(node.getUpperLeft().getRectangle())) {
                recursiveDestructiveCollect(imageNames, node.getUpperLeft(), dpp, query);
            }
            if (query.intersects(node.getUpperRight().getRectangle())) {
                recursiveDestructiveCollect(imageNames, node.getUpperRight(), dpp, query);
            }
            if (query.intersects(node.getLowerLeft().getRectangle())) {
                recursiveDestructiveCollect(imageNames, node.getLowerLeft(), dpp, query);
            }
            if (query.intersects(node.getLowerRight().getRectangle())) {
                recursiveDestructiveCollect(imageNames, node.getLowerRight(), dpp, query);
            }
        }
    }

    private boolean isDeepEnough(QuadTreeNode node, double dpp) {
        return node.getLongitudinalDistPerPixel() >= dpp|| node.getDepth() == QuadTreeNode.DEEPEST_DEPTH;
    }
}
