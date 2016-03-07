package editor;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Jesse on 3/5/2016.
 */
public class MouseEventHandler implements EventHandler<MouseEvent> {
    Cursor cursor;
    TextContainer textBuffer;
    KeyEventHandler keyEventHandler;
    ScrollBarHandler scrollBarHandler;

    public MouseEventHandler(Cursor cursor, TextContainer textBuffer, EventHandler keyEventHandler, ScrollBarHandler scrollBarHandler) {
        this.cursor = cursor;
        this.textBuffer = textBuffer;
        this.keyEventHandler = (KeyEventHandler) keyEventHandler;
        this.scrollBarHandler = scrollBarHandler;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        double mousePressedX = mouseEvent.getX();
        double mousePressedY = mouseEvent.getY();
        EventType eventType = mouseEvent.getEventType();
        if (eventType == MouseEvent.MOUSE_PRESSED) {
            adjustCursor(mousePressedX, mousePressedY);
        } else if (eventType == MouseEvent.MOUSE_DRAGGED) {
            //ec implementation
        } else if (eventType == MouseEvent.MOUSE_RELEASED) {
            // ec
        }
    }

    public void adjustCursor(double mousePressedX, double mousePressedY) {
        //need to check that everything is still on the same line before searching for closest
        Node node = searchVertical(mousePressedY + scrollBarHandler.getChange());

        if (node.item == null) {
            return;
        }

        final double lineYPos = ((Text) node.item).getY();

        while (node.item != null && ((Text) node.item).getY() == lineYPos) {
            if (((Text) node.item).getX() + ((Text) node.item).getLayoutBounds().getWidth()/2 >= mousePressedX) {
                keyEventHandler.moveCursor(node.previous);
                return;
            }
            node = node.next;
        }

        keyEventHandler.moveCursor(node.previous);
    }

    public Node searchVertical(double mousePressedY) {
        ArrayList<NewLinePosition> linePositions = textBuffer.getLinePositions();

        if (mousePressedY < linePositions.get(0).getPositionOfTopLeftCorner()) {
            return linePositions.get(0).getFirstNodeInLine();
        } else if (mousePressedY > linePositions.get(linePositions.size() - 1).getPositionOfTopLeftCorner()) {
            return linePositions.get(linePositions.size() - 1).getFirstNodeInLine();
        }

        for(int i = 0; i < linePositions.size() - 1; i++) {
            NewLinePosition newLinePosition = linePositions.get(i);
            NewLinePosition nextNewLinePosition = linePositions.get(i + 1);
            double lowerBound = newLinePosition.getPositionOfTopLeftCorner();
            double upperBound = nextNewLinePosition.getPositionOfTopLeftCorner();
            if (lowerBound <= mousePressedY && mousePressedY < upperBound) {
                return newLinePosition.getFirstNodeInLine();
            }
        }
        throw new RuntimeException("search vertical returns an unexpected value");
    }
}