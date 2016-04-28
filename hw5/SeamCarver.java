import java.awt.Color;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Picture;

/**
 * Created by Jesse on 4/24/2016.
 */
public class SeamCarver {
    private Picture picture;
    private Picture transposed;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        transposed = getTransposedMatrix(picture);
    }

    private Picture getTransposedMatrix(Picture pic) {
        int n = pic.height();
        int m = pic.width();

        Picture transposedMatrix = new Picture(n, m);

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                transposedMatrix.set(x, y, pic.get(y, x));
            }
        }

        return transposedMatrix;
    }

    private void transpose() {
        Picture old = picture;
        picture = transposed;
        transposed = old;
    }

    public Picture picture() {
        return new Picture(picture);
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

        return getSquaredAbsDifference(top.getBlue(), bottom.getBlue())
                + getSquaredAbsDifference(top.getGreen(), bottom.getGreen())
                + getSquaredAbsDifference(top.getRed(), bottom.getRed())
                + getSquaredAbsDifference(left.getBlue(), right.getBlue())
                + getSquaredAbsDifference(left.getGreen(), right.getGreen())
                + getSquaredAbsDifference(left.getRed(), right.getRed());
    }
    private double getSquaredAbsDifference(int colorValue, int otherColorValue) {
        return (colorValue - otherColorValue) * (colorValue - otherColorValue);
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
        transpose();
        Node[][] energyMatrix = findEnergyMatrixVertical();
        ArrayList<Node> path = new ArrayList<>();
        Node minLastNode = energyMatrix[width() - 1][height() - 1];
        for (int x = 0; x < width(); x++) {
            Node node = energyMatrix[x][height() - 1];
            if (node.getEnergy() < minLastNode.getEnergy()) {
                minLastNode = node;
            }
        }
        while (minLastNode != null) {
            path.add(0, minLastNode);
            minLastNode = minLastNode.previous;
        }
        int[] p = new int[height()];
        for (int i = 0; i < height(); i++) {
            p[i] = path.get(i).getX();
        }
        transpose();
        return p;
    }

    private class Node {
        public Node(int x, int y, Node previous, double energy) {
            this.x = x;
            this.y = y;
            this.previous = previous;
            this.energy = energy;
        }

        int x, y;
        double energy;
        Node previous;

        public void setEnergy(double energy) {
            this.energy = energy;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double getEnergy() {
            return energy;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public String toString() {
            return String.valueOf(energy);
        }
    }

    private int[] findVerticalSeam() {
        Node[][] energyMatrix = findEnergyMatrixVertical();
        ArrayList<Node> path = new ArrayList<>();
        Node minLastNode = energyMatrix[width() - 1][height() - 1];
        for (int x = 0; x < width(); x++) {
            Node node = energyMatrix[x][height() - 1];
            if (node.getEnergy() < minLastNode.getEnergy()) {
                minLastNode = node;
            }
        }
        while (minLastNode != null) {
            path.add(0, minLastNode);
            minLastNode = minLastNode.previous;
        }
        int[] p = new int[height()];
        for (int i = 0; i < height(); i++) {
            p[i] = path.get(i).getX();
        }
        return p;
    }

    public Node[][] findEnergyMatrixVertical() {
        Node[][] energyMatrix = new Node[width()][height()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energyMatrix[x][y] = getEnergyNode(x, y, energyMatrix);
            }
        }
        return energyMatrix;
    }

    private Node getEnergyNode(int x, int y, Node[][] energyMatrix) {
        double currentEnergy = energy(x, y);
        double minPathEnergy = Double.MAX_VALUE;
        Node previous = null;
        try {
            Node node = energyMatrix[x - 1][y - 1];
            if (node.getEnergy() < minPathEnergy) {
                minPathEnergy = node.getEnergy();
                previous = node;
            }
        } catch (IndexOutOfBoundsException i) {
            i = null;
        }
        try {
            Node node = energyMatrix[x][y - 1];
            if (node.getEnergy() < minPathEnergy) {
                minPathEnergy = node.getEnergy();
                previous = node;
            }
        } catch (IndexOutOfBoundsException i) {
            i = null;
        }
        try {
            Node node = energyMatrix[x + 1][y - 1];
            if (node.getEnergy() < minPathEnergy) {
                minPathEnergy = node.getEnergy();
                previous = node;
            }
        } catch (IndexOutOfBoundsException i) {
            i = null;
        }
        if (previous == null) {
            minPathEnergy = 0;
        }
        return new Node(x, y, previous, minPathEnergy + currentEnergy);
    }

    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(picture, seam);
    }
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(picture, seam);
    }
}
