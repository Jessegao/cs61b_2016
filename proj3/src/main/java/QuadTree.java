import java.io.File;
import java.util.ArrayList;
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

    public ArrayList<QuadTreeNode> getRasterImages(Double top, Double left, Double bottom, Double right, Double width) {
        ArrayList<QuadTreeNode> tileNodes = new ArrayList<>();
        Rectangle query = new Rectangle(left, right, top, bottom);
        double dpp = Math.abs(left - right) / width;
        recursiveDestructiveCollect(tileNodes, root, dpp, query);
        return tileNodes;
    }

    /** Recursively gets all the tiles' nodes (no root/ or .png added yet) and puts them into tileNodes */
    private void recursiveDestructiveCollect(ArrayList<QuadTreeNode> tileNodes, QuadTreeNode node, double dpp, Rectangle query) {
        if (isDeepEnough(node, dpp)) { // inefficient
            tileNodes.add(node);
        } else {
            if (query.intersects(node.getUpperLeft().getRectangle())) {
                recursiveDestructiveCollect(tileNodes, node.getUpperLeft(), dpp, query);
            }
            if (query.intersects(node.getUpperRight().getRectangle())) {
                recursiveDestructiveCollect(tileNodes, node.getUpperRight(), dpp, query);
            }
            if (query.intersects(node.getLowerLeft().getRectangle())) {
                recursiveDestructiveCollect(tileNodes, node.getLowerLeft(), dpp, query);
            }
            if (query.intersects(node.getLowerRight().getRectangle())) {
                recursiveDestructiveCollect(tileNodes, node.getLowerRight(), dpp, query);
            }
        }
    }

    private boolean isDeepEnough(QuadTreeNode node, double dpp) {
        return node.getLongitudinalDistPerPixel() <= dpp|| node.getDepth() == QuadTreeNode.DEEPEST_DEPTH;
    }
}
