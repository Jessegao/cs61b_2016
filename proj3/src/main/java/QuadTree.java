import java.io.File;

/**
 * Created by Jesse on 4/13/2016.
 */
public class QuadTree {
    private QuadTreeNode root;
    private static final int DEPTH_ROOT = 0;

    public QuadTree(double ROOT_ULLAT, double ROOT_ULLON, double ROOT_LRLAT, double ROOT_LRLON, File rootFile) {
        root = new QuadTreeNode(DEPTH_ROOT, ROOT_ULLAT, ROOT_ULLON, ROOT_LRLAT, ROOT_LRLON, rootFile);
    }
}
