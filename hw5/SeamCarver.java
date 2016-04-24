import java.awt.*;

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

        Color colorMinus = picture.get(xminus, yminus);
        Color colorPlus = picture.get(xplus, yplus);

        double en = getAbsDifference(colorMinus.getBlue(), colorPlus.getBlue()) + getAbsDifference(colorMinus.getGreen(), colorPlus.getGreen()) + getAbsDifference(colorMinus.getRed(), colorPlus.getRed());

        return Math.pow(en, 2);
    }
    private int getAbsDifference(int colorValue, int otherColorValue) {
        return Math.abs(colorValue - otherColorValue);
    }
    private int wrap(int i, int wraparound) {
        if (i < 0) {
            return wraparound + i;
        } else if (i > wraparound) {
            return i % wraparound;
        } else {
            return i;
        }
    }
    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam
    public int[] findVerticalSeam()              // sequence of indices for vertical seam
    public void removeHorizontalSeam(int[] seam)   // remove horizontal seam from picture
    public void removeVerticalSeam(int[] seam)
}
