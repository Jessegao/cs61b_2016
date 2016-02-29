package editor;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by jesse on 2/25/16.
 * This class will contain all the text and calculate where to display stuff
 */
public class TextContainer {
    private LinkedList<Text> container;

    private int margins = 5;

    public TextContainer() {
        container = new LinkedList<Text>();
    }

    public TextContainer(String s) {
        container = new LinkedList<Text>();
        container.add(new Text(s));
    }

    //two insert methods for easy adding of new elements
    public void insert(String s) {
        Text t = new Text(s);
        t.setTextOrigin(VPos.TOP);
        container.add(t);
    }

    public void insert(String s, int i) {
        Text t = new Text(s);
        t.setTextOrigin(VPos.TOP);
        container.add(i, t);
    }
    //ALWAYS REMEMBER TO UPDATE BOTH OVERLOADED METHODS
    public void render(final Group root, int windowWidth, int windowHeight) {
        //update eventually so that it only renders from the cursor position
        double renderingPosX = margins;
        double renderingPosY = 0.0;

        for(Text t : container) {
            //set up where each text object is
            t.setX(renderingPosX + t.getLayoutBounds().getWidth()/2.0);
            renderingPosX += t.getLayoutBounds().getWidth();
            t.setY(renderingPosY + t.getLayoutBounds().getHeight()/2.0);
            //remember to adjust y once word wrapping is implemented
            if (!root.getChildren().contains(t)) {
                root.getChildren().add(t);
                t.toFront();
            }
        }
    }

    //doesnt adjust any lines based on window dimensions
    public void render(final Group root) {
        //update eventually so that it only renders from the cursor position
        double renderingPosX = 0.0;
        double renderingPosY = 0.0;

        for(Text t : container) {
            //set up where each text object is
            t.setX(renderingPosX + t.getLayoutBounds().getWidth()/2.0);
            renderingPosX += t.getLayoutBounds().getWidth();
            t.setY(renderingPosY + t.getLayoutBounds().getHeight()/2.0);
            //remember to adjust y once word wrapping is implemented
            if (!root.getChildren().contains(t)) {
                root.getChildren().add(t);
                t.toFront();
            }
        }
    }

    public void setFont(Font font) {
        for (Text t : container) {
            t.setFont(font);
        }
    }
}

