package editor;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by jesse on 2/25/16.
 * This class will contain all the text and calculate where to display stuff
 */
public class TextContainer {
    private LinkedListDeque<Text> container;
    //stores the beginning and end of each line. renewed every time render is called
    private LinkedListDeque<NewLinePosition> linePositions;
    private static final int MARGIN = 5;
    private int margins = MARGIN;
    private Group root;
    private Font font;

    public TextContainer(Group root) {
        this.root = root;
        container = new LinkedListDeque<Text>();
    }

    public static int getMARGIN() {
        return MARGIN;
    }

    public Text get(int i) {
        return container.get(i);
    }

    //used for the first call from cursor's constructor
    public Node<Text> getFirst() {
        return container.getFirst();
    }

    public void insert(String s, Node n) {
        Text t = new Text(s);
        t.setFont(font);
        t.setTextOrigin(VPos.TOP);
        container.insertAtNode(t, n);
        root.getChildren().add(t);
        t.toFront();
    }

    public void remove(Node node) {
        root.getChildren().remove(container.removeAtNode(node));
    }

    public void render(int windowWidth, int windowHeight) {
        double renderingPosX = margins;
        double renderingPosY = 0.0;
        Node node = getFirst();

        //initialize new lineposition tracker to keep track of where the start of every line is
        linePositions = new LinkedListDeque<NewLinePosition>();
        linePositions.addLast(new NewLinePosition(renderingPosY, node));

        while(node.next.item != null) {
            //remember to update node and text together
            node = node.next;
            Text t = (Text) node.item;
            //set up text wrapping
            if (calcEdgePos(renderingPosX, t.getLayoutBounds().getWidth()) > windowWidth && !t.getText().equals(" ")) {

                Node backtracked = findIndentNode(node);
                //if backtrack does find a node to be indented, it will set the new line's position for the node
                if(backtracked != null) {
                    //points the rendering position back to where it needs to re-render
                    node = backtracked;
                    t = (Text) backtracked.item;
                    //creates new line
                    renderingPosX = margins;
                    renderingPosY += t.getLayoutBounds().getHeight();
                    //adds new element to linePositions
                    linePositions.addLast(new NewLinePosition(renderingPosY, node));
                } else {
                    renderingPosX = margins;
                    renderingPosY += t.getLayoutBounds().getHeight();
                    //adds new element to linePositions
                    linePositions.addLast(new NewLinePosition(renderingPosY, node));
                }

            } else if(t.getText().equals("\r")) {
                //creates new line
                renderingPosX = margins;
                //check here if spacing becomes weird
                renderingPosY += t.getLayoutBounds().getHeight()/2;
            }
            //rounding the positions
            renderingPosX = Math.round(renderingPosX);
            renderingPosY = Math.round(renderingPosY);
            t.setX(renderingPosX);
            renderingPosX += t.getLayoutBounds().getWidth();
            t.setY(renderingPosY);

            //bad, takes n squared time
            //if (!root.getChildren().contains(t)) {
            //    root.getChildren().add(t);
            //    t.toFront();
            //}
        }
    }

    //calculates the right edge of the text including the margin
    private double calcEdgePos(double leftPos, double width) {
        return leftPos + width + margins;
    }

    //returns the node at which you should abstractly indent the text to make it wrap. returns null if
    //the line cannot be indented.
    private Node findIndentNode(Node n) {
        Node node = n;
        Text text = (Text) n.item;
        //backtracks until it reaches a space or until it reaches the beginning of the line
        while(text.getX() != margins && !text.getText().equals(" ")) {
            node = node.previous;
            text = (Text) node.item;
        }
        if(text.getX() == margins) {
            return null;
        } else {
            return node.next;
        }
    }

    public void setFont(Font font) {
        this.font = font;
    }
}

