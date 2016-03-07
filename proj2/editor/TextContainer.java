package editor;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by jesse on 2/25/16.
 * This class will contain all the text and calculate where to display stuff
 */
public class TextContainer {
    private LinkedListDeque<Text> container;

    //stores the beginning of each line. cleared every time render is called
    private ArrayList<NewLinePosition> linePositions = new ArrayList<NewLinePosition>();

    private static final int MARGIN = 5;

    private int leftMargin = MARGIN;
    private int rightMargin;
    private Group root;

    private Font font;
    private ScrollBarHandler scrollBarHandler;
    public TextContainer(Group root, ScrollBarHandler scroller) {
        this.root = root;
        container = new LinkedListDeque<Text>();
        scrollBarHandler = scroller;
        rightMargin = (int) scroller.getScrollBar().getLayoutBounds().getWidth() + leftMargin;
    }

    public ArrayList<NewLinePosition> getLinePositions() {
        return linePositions;
    }

    public int getRightMargin() {
        return rightMargin;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public Text get(int i) {
        return container.get(i);
    }

    //used for the first call from cursor's constructor
    public Node<Text> getFirst() {
        return container.getFirst();
    }

    public void insertNodeAtNode(Node<Text> insert, Node insertionPoint) {
        container.insertNodeAtNode(insert, insertionPoint);
        root.getChildren().add(insert.item);
        insert.item.toFront();
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
        double renderingPosX = leftMargin;
        double renderingPosY = 0.0;
        Node node = getFirst();

        //refreshes list every render
        linePositions.clear();

        while(node.next.item != null) {
            //remember to update node and text together
            node = node.next;
            Text t = (Text) node.item;
            //set up text wrapping
            if (calcEdgePos(renderingPosX, t.getLayoutBounds().getWidth()) > windowWidth && !t.getText().equals(" ") && !(t.getText().equals("\r") || t.getText().equals("\n"))) {//This is excluding spaces and newlines that go beyond the window's boundaries
                Node backtracked = findIndentNode(node);
                //if backtrack does find a node to be indented, it will set the new line's position for the node
                if (backtracked != null) {
                    //points the rendering position back to where it needs to re-render
                    node = backtracked;
                    t = (Text) backtracked.item;
                    //creates new line
                    renderingPosX = leftMargin;
                    renderingPosY += t.getLayoutBounds().getHeight();
                } else {
                    renderingPosX = leftMargin;
                    renderingPosY += t.getLayoutBounds().getHeight();
                }

            } else if(t.getText().equals("\r") || t.getText().equals("\n")) {
                //creates new line
                renderingPosX = leftMargin;
                //check here if spacing becomes weird
                renderingPosY += t.getLayoutBounds().getHeight()/2;
            }

            //adds beginning positions
            if (renderingPosX == leftMargin) {
                linePositions.add(new NewLinePosition(renderingPosY, node));
            }

            //rounding the positions
            renderingPosX = Math.round(renderingPosX);
            renderingPosY = Math.round(renderingPosY);
            t.setX(renderingPosX);
            renderingPosX += t.getLayoutBounds().getWidth();
            t.setY(renderingPosY);
        }

        //at the very end, tells the scrollbar to change according to how long the text is
        scrollBarHandler.updateMax();
    }

    //calculates the right edge of the text including the margin
    private double calcEdgePos(double leftPos, double width) {
        return leftPos + width + rightMargin;
    }

    //returns the node at which you should abstractly indent the text to make it wrap. returns null if
    //the line cannot be indented.
    private Node findIndentNode(Node n) {
        Node node = n;
        Text text = (Text) n.item;
        //backtracks until it reaches a space or until it reaches the beginning of the line
        while(text.getX() != leftMargin && !text.getText().equals(" ")) {
            node = node.previous;
            text = (Text) node.item;
        }
        if(text.getX() == leftMargin) {
            return null;
        } else {
            return node.next;
        }
    }

    public void setFont(Font font) {
        this.font = font;
    }
}

