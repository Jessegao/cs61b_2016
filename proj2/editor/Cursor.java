package editor;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.StringConverter;

/**
 * Created by jesse on 2/29/16.
 */
public class Cursor {
    //position of the cursor among the characters in the textbuffer
    private int listPosition = 0;
    //Node right before the cursor
    private Node<Text> node;
    private double renderPosX;

    private double renderPosY;

    private double leftMargin;
    private double rightMargin;
    private Rectangle cursor;
    //keeps track of what the last action was for ambiguous line placement
    private boolean shouldStayOnLine = false;

    private final static double CURSORWIDTH = 1.0;

    private static final int STARTING_FONT_SIZE = 12;
    private Group root;
    private TextContainer container;

    public Cursor(Group root, TextContainer t){
        this.root = root;
        container = t;
        leftMargin = t.getLeftMargin();
        rightMargin = t.getRightMargin();
        renderPosX = leftMargin;
        renderPosY = 0;
        cursor = new Rectangle(CURSORWIDTH, STARTING_FONT_SIZE);
        cursor.setX(renderPosX);
        node = container.getFirst();
    }

    public int getListPosition(){
        return listPosition;
    }

    public double getRenderPosY() {
        return renderPosY;
    }

    public double getRenderPosYBottom() {
        return renderPosY + cursor.getHeight();
    }

    public String getPosition() {
        return (int)renderPosX + ", " + (int)renderPosY;
    }

    public void moveTo(Node n) {
        node = n;
    }

    //called by the EventHandler so that the cursor will insert where it is at. Might need to overload for copy and paste
    public void insert(String s) {
        //must insert and update current node
        container.insert(s, node);
        node = node.next;
        listPosition++;
        shouldStayOnLine = false;
    }

    public void remove() {
        listPosition--;
        if (listPosition >= 0) {
            //decides if the cursor should stay on the same line
            if(((Text) node.previous.getItem()) != null && ((Text) node.getItem()).getX() == leftMargin && ((Text) node.previous.getItem()).getText().equals(" ")) {
                shouldStayOnLine = true;
            }
            Node oldnode = node;
            node = node.previous;
            container.remove(oldnode);
        } else {
            listPosition = 0;
        }
    }


    public void render(int windowWidth, int windowHeight){
        //change so that it would adjust to the correct leftMargin
        if (shouldStayOnLine) {
            renderPosX = leftMargin;
            shouldStayOnLine = false;
        } else if (node.item != null && node.item.getText().equals(" ") && node.item.getX() + node.item.getLayoutBounds().getWidth() + rightMargin > windowWidth) { //if cursor is on a space character beyond the bounds
            renderPosX = windowWidth - rightMargin;
            renderPosY = ((Text) node.item).getY();
        } else if (node.item != null){
            renderPosX = ((Text) node.item).getLayoutBounds().getWidth() + ((Text) node.item).getX();
            renderPosY = ((Text) node.item).getY();
        } else {
            renderPosX = leftMargin;
            renderPosY = 0;
        }
        cursor.setX(renderPosX);
        cursor.setY(renderPosY);
    }

    public void firstRender() {
        root.getChildren().add(cursor);
        cursor.toFront();
    }

    /** An EventHandler to handle blinking. */
    public void setFont(Font f, int windowWidth, int windowHeight){
        Text t = new Text(" ");
        t.setFont(f);
        root.getChildren().remove(cursor);
        cursor = new Rectangle(CURSORWIDTH, t.getLayoutBounds().getHeight());
        render(windowWidth, windowHeight);
    }

    public void moveLeft() {
        if(node.item != null) {
            listPosition--;
            node = node.previous;
        }
    }

    public void moveRight() {
        if(node.next.item != null) {
            listPosition++;
            node = node.next;
        }
    }

    private class RectangleBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors =
                {Color.BLACK, Color.WHITE};

        RectangleBlinkEventHandler() {
            // Set the color to be the first color in the list.
            changeColor();
        }

        private void changeColor() {
            cursor.setFill(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }

    /** Makes the text bounding box change color periodically. */
    public void blink() {
        // Create a Timeline that will call the "handle" function of RectangleBlinkEventHandler
        // every 1 second.
        final Timeline timeline = new Timeline();
        // The rectangle should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        RectangleBlinkEventHandler cursorChange = new RectangleBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
