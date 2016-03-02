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
    private static final int MARGIN = 5;
    private int margins = MARGIN;
    private Group root;

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
        t.setTextOrigin(VPos.TOP);
        container.insertAtNode(t, n);
    }

    public void remove(Node node) {
        root.getChildren().remove(container.removeAtNode(node));
    }

    //ALWAYS REMEMBER TO UPDATE BOTH OVERLOADED METHODS
    public void render(int windowWidth, int windowHeight) {
        double renderingPosX = margins;
        double renderingPosY = 0.0;

        for(int i = 0; i < container.size(); i++) {
            Text t = container.get(i);
            //set up where each text object is
            t.setX(renderingPosX);
            renderingPosX += t.getLayoutBounds().getWidth();
            t.setY(renderingPosY);
            //remember to adjust y once word wrapping is implemented
            if (!root.getChildren().contains(t)) {
                root.getChildren().add(t);
                t.toFront();
            }
        }
    }

    public void setFont(Font font) {
        for(int i = 0; i < container.size(); i++) {
            Text t = container.get(i);
            t.setFont(font);
        }
    }
}

