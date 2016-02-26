package editor;

import java.util.ArrayList;

import javafx.geometry.VPos;
import javafx.scene.text.Text;

/**
 * Created by jesse on 2/25/16.
 * This class will contain all the text and calculate where to display stuff
 */
public class TextContainer {
    private ArrayList<Text> container;
    private int margins = 5;

    public TextContainer() {
        container = new ArrayList<Text>();
    }

    public TextContainer(String s) {
        container = new ArrayList<Text>();
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

    public void display() {
        for(Text t : container) {
            t.se
        }
    }
}

