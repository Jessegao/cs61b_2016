package editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;

/**
 * Created by Jesse on 3/4/2016.
 */
public class ScrollBarHandler {

    private ScrollBar scrollBar;

    private Group root;
    private Group textRoot;
    private int windowHeight;

    public double getChange() {
        return change;
    }

    private double change;

    public ScrollBar getScrollBar() {
        return scrollBar;
    }

    public void updateXPos(int windowWidth) {
        double usableScreenWidth = windowWidth - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(usableScreenWidth);
    }

    public void updateHeight(int windowHeight) {
        scrollBar.setPrefHeight(windowHeight);
    }

    public void updateMax() {
        scrollBar.setMax(Math.max(0, (int) textRoot.getLayoutBounds().getHeight() - windowHeight));
    }

    public void snapCursor(Cursor cursor) {
        double pixelsBeyondBoundaries;
        if (cursor.getRenderPosY() - change > windowHeight) {
            pixelsBeyondBoundaries = cursor.getRenderPosYBottom() - change - windowHeight;
        } else {
            pixelsBeyondBoundaries = (cursor.getRenderPosY() - change);
        }
        scrollBar.setValue(change + pixelsBeyondBoundaries);
    }

    public ScrollBarHandler(Group root, Group textRoot, int WINDOW_WIDTH, int WINDOW_HEIGHT) {
        this.root = root;
        this.textRoot = textRoot;
        // Make a vertical scroll bar on the right side of the screen.
        scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        // Set the height of the scroll bar so that it fills the whole window.
        scrollBar.setPrefHeight(WINDOW_HEIGHT);
        windowHeight = WINDOW_HEIGHT;
        // Set the range of the scroll bar.
        scrollBar.setMin(0);
        updateMax();

        double usableScreenWidth = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(usableScreenWidth);

        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                // newValue describes the value of the new position of the scroll bar. The numerical
                // value of the position is based on the position of the scroll bar, and on the min
                // and max we set above. For example, if the scroll bar is exactly in the middle of
                // the scroll area, the position will be:
                //      scroll minimum + (scroll maximum - scroll minimum) / 2
                updateMax();
                change = newValue.doubleValue();
                textRoot.setLayoutY(Math.round(-newValue.doubleValue()));
            }
        });

        // Add the scroll bar to the scene graph, so that it appears on the screen.
        root.getChildren().add(scrollBar);
    }
}
