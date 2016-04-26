import java.awt.Color;
import java.util.LinkedList;

import edu.princeton.cs.algs4.Picture;

/**
 * Created by Jesse on 4/24/2016.
 */
public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }
    public double energy(int x, int y) {
        int xminus = wrap(x - 1, width());
        int xplus = wrap(x + 1, width());
        int yminus = wrap(y - 1, height());
        int yplus = wrap(y + 1, height());

        Color top = picture.get(x, yplus);
        Color bottom = picture.get(x, yminus);
        Color left = picture.get(xminus, y);
        Color right = picture.get(xplus, y);

        double enTopBottom = getSquaredAbsDifference(top.getBlue(), bottom.getBlue()) + getSquaredAbsDifference(top.getGreen(), bottom.getGreen())
                + getSquaredAbsDifference(top.getRed(), bottom.getRed());
        double enLeftRight = getSquaredAbsDifference(left.getBlue(), right.getBlue()) + getSquaredAbsDifference(left.getGreen(), right.getGreen())
                + getSquaredAbsDifference(left.getRed(), right.getRed());

        return enLeftRight + enTopBottom;
    }
    private double getSquaredAbsDifference(int colorValue, int otherColorValue) {
        return Math.pow((colorValue - otherColorValue), 2);
    }
    private int wrap(int i, int wraparound) {
        if (i < 0) {
            return wraparound + i;
        } else if (i >= wraparound) {
            return i % wraparound;
        } else {
            return i;
        }
    }

    public int[] findHorizontalSeam() {
        return null;
    }
    private class Node {
        public Node(int x, int y, double energy, ) {
            this.x = x;
            this.y = y;
            this.energy = energy;
        }

        int x, y;
        double energy;
        Node previous;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double getEnergy() {
            return energy;
        }
    }
    public int[] findVerticalSeam() {
        LinkedList<Node[]> paths = new LinkedList<>();
//        for (int row = width()-1; row >= 0; row--) {
//            Node[] path = new Node[height()];
//            for (int col = 0; col < width(); col++) {
//                Node node = new Node(col, row, energy(col, row));
//
//            }
//        }
        int row = height() - 1;
        for (int col = 0; col < width(); col++) {
            Node node = new Node(col, row, energy(col, row));
            paths.add(findPath(node));
        }
        return ;
    }
    private Node[] findPath(Node bottomNode) {
        Node[] path = new Node[height()];
        for (int row = width()-1; row >= 0; row--) {

        }
    }
    private Node findNextPathNode(Node currentNode) {
        if (node.getY() == 0) {
            return null;
        }
        else {

        }
    }


    public void removeHorizontalSeam(int[] seam) {

    }
    public void removeVerticalSeam(int[] seam) {

    }
}
