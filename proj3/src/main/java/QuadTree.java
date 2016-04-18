import java.util.ArrayList;

/**
 * Created by Jesse on 4/13/2016.
 */
public class QuadTree {
    private QuadTreeNode root;
    private static final int DEPTH_ROOT = 0;

    public QuadTree(double rootullat, double rootullon,
                    double rootlrat, double rootlrlon, int rootFile) {
        root = new QuadTreeNode(DEPTH_ROOT, rootullat,
                rootullon, rootlrat, rootlrlon, rootFile);
    }

    public String toString() {
        return root.toString();
    }

    public ArrayList<QuadTreeNode> getRasterImages(Double top,
                                                   Double left,
                                                   Double bottom,
                                                   Double right,
                                                   Double width) {
        ArrayList<QuadTreeNode> tileNodes = new ArrayList<>();
        Rectangle query = new Rectangle(left, right, top, bottom);
        double dpp = Math.abs(left - right) / width;
        recursiveDestructiveCollect(tileNodes, root, dpp, query);
        return tileNodes;
    }

    private void recursiveDestructiveCollect(ArrayList<QuadTreeNode> tileNodes,
                                             QuadTreeNode node, double dpp, Rectangle query) {
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
        return node.getLongitudinalDistPerPixel() <= dpp
                || node.getDepth() == QuadTreeNode.DEEPEST_DEPTH;
    }
}
