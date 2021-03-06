/**
 * Created by Jesse on 4/15/2016.
 */
public class Rectangle {

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    private double left;
    private double right;
    private double top;
    private double bottom;

    public Rectangle(double left, double right, double top, double bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public boolean intersects(Rectangle rectangle) {
        /** This basically translated from code I found online */
        return (rectangle.getLeft() < right && rectangle.getRight() > left
                && rectangle.getTop() > bottom && rectangle.getBottom() < top);
    }
}
