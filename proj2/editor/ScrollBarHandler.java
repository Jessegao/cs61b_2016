package editor;

import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;

/**
 * Created by Jesse on 3/4/2016.
 */
public class ScrollBarHandler {

    private ScrollBar scrollBar;

    public ScrollBarHandler(Group root, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        // Make a vertical scroll bar on the right side of the screen.
        scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        // Set the height of the scroll bar so that it fills the whole window.
        scrollBar.setPrefHeight(WINDOW_HEIGHT);

        // Set the range of the scroll bar.
        scrollBar.setMin(WINDOW_HEIGHT);
        scrollBar.setMax(WINDOW_HEIGHT);

        double usableScreenWidth = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(usableScreenWidth);

        // Add the scroll bar to the scene graph, so that it appears on the screen.
        root.getChildren().add(scrollBar);
    }
}
