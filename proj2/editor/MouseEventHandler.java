package editor;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Created by Jesse on 3/5/2016.
 */
public class MouseEventHandler implements EventHandler<MouseEvent> {
    Cursor cursor;
    TextContainer textBuffer;
    KeyEventHandler keyEventHandler;

    public MouseEventHandler(Cursor cursor, TextContainer textBuffer, EventHandler keyEventHandler) {
        this.cursor = cursor;
        this.textBuffer = textBuffer;
        this.keyEventHandler = (KeyEventHandler) keyEventHandler;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        double mousePressedX = mouseEvent.getX();
        double mousePressedY = mouseEvent.getY();
        EventType eventType = mouseEvent.getEventType();
        if (eventType == MouseEvent.MOUSE_PRESSED) {
            // needs to adjust cursor
        } else if (eventType == MouseEvent.MOUSE_DRAGGED) {
            //ec implementation
        } else if (eventType == MouseEvent.MOUSE_RELEASED) {
            // ec
        }
    }

    public void adjustCursor(double mousePressedX, double mousePressedY) {
        //need to check that everything is still on the same line before searching for closest
    }

    public Node searchVertical(double mousePressedY) {
        ArrayList<NewLinePosition> linePositions = textBuffer.getLinePositions();

        NewLinePosition closestLinePos = linePositions.get(0);
        double distance = closestLinePos.getPositionOfTopLeftCorner() - mousePressedY;

        for(NewLinePosition newLinePosition : linePositions) {
            double newdistance = newLinePosition.getPositionOfTopLeftCorner() - mousePressedY;
            
        }
    }
}
