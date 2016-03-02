package editor;

/**
 * Created by jesse on 3/1/16.
 */
public class NewLinePosition {
    private double positionOfTopLeftCorner;
    private Node firstNodeInLine;

    public NewLinePosition(double positionOfTopLeftCorner, Node node) {
        this.positionOfTopLeftCorner = positionOfTopLeftCorner;
        firstNodeInLine = node;
    }

    public double getPositionOfTopLeftCorner() {
        return positionOfTopLeftCorner;
    }

    public Node getFirstNodeInLine() {
        return firstNodeInLine;
    }
}
