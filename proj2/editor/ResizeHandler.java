package editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;

/**
 * Created by Jesse on 3/5/2016.
 */
public class ResizeHandler {
    public ResizeHandler(Scene scene, KeyEventHandler keyEventHandler, ScrollBarHandler scrollBarHandler) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenWidth,
                    Number newScreenWidth) {
                keyEventHandler.changeWindowWidth(newScreenWidth.intValue());
                keyEventHandler.render();
                scrollBarHandler.updateXPos(newScreenWidth.intValue());
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenHeight,
                    Number newScreenHeight) {
                keyEventHandler.changeWindowHeight(newScreenHeight.intValue());
                keyEventHandler.render();
                scrollBarHandler.updateHeight(newScreenHeight.intValue());
            }
        });
    }
}
