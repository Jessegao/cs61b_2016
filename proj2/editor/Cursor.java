package editor;
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
    private Rectangle cursor;

    private final static double CURSORWIDTH = 1.0;
    private static final int STARTING_FONT_SIZE = 12;

    private Group root;
    private TextContainer container;

    public Cursor(Group root, TextContainer t){
        this.root = root;
        container = t;
        renderPosX = TextContainer.getMARGIN();
        renderPosY = 0;
        cursor = new Rectangle(CURSORWIDTH, STARTING_FONT_SIZE);
        node = container.getFirst();
    }

    public int getListPosition(){
        return listPosition;
    }

    //called by the EventHandler so that the cursor will insert where it is at. Might need to overload for copy and paste
    public void insert(String s) {
        //must insert and update current node
        container.insert(s, node);
        node = node.next;
        listPosition++;
    }

    public void remove() {
        listPosition--;
        if (listPosition >= 0) {
            Node oldnode = node;
            node = node.previous;
            container.remove(oldnode);
        } else {
            listPosition = 0;
        }
    }

    public void render(){
        if (node.item != null) {
            renderPosX = ((Text) node.item).getLayoutBounds().getWidth() + ((Text) node.item).getX() + 1;
            renderPosY = ((Text) node.item).getY();
        } else {
            renderPosX = TextContainer.getMARGIN();
            renderPosY = 0;
        }
        cursor.setX(renderPosX);
        cursor.setY(renderPosY);
        if(!root.getChildren().contains(cursor)){
            root.getChildren().add(cursor);
        }
    }

    /** An EventHandler to handle blinking. */
    public void setFont(Font f){
        Text t = new Text(" ");
        t.setFont(f);
        root.getChildren().remove(cursor);
        cursor = new Rectangle(CURSORWIDTH, t.getLayoutBounds().getHeight());
        render();
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
