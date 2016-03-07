
/**
 * Created by jesse on 2/25/16.
 * Doesn't use the TextContainer Class yet
 */
package editor;
import javafx.event.EventHandler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Stack;

/** An EventHandler to handle keys that get pressed. */
public class KeyEventHandler implements EventHandler<KeyEvent> {

    /** The Text to display on the screen. */
    private TextContainer textBuffer;
    private Cursor cursor;
    FileManager fileManager;
    ScrollBarHandler scrollBarHandler;
    MouseEventHandler mouseEventHandler;

    private int windowWidth;
    private int windowHeight;

    //undo redo
    private Stack<Action> undoStack;
    private Stack<Action> redoStack;

    private static final int STARTING_FONT_SIZE = 12;

    private int fontSize = STARTING_FONT_SIZE;

    private String fontName = "Verdana";

    //remember to call render after every change made

    public KeyEventHandler(int windowWidth, int windowHeight, TextContainer t, Cursor c, FileManager f, ScrollBarHandler s) {
        textBuffer = t;
        textBuffer.setFont(Font.font(fontName, fontSize));
        textBuffer.render(windowWidth, windowHeight);
        cursor = c;
        //make cursor appear and blink
        cursor.firstRender();
        cursor.blink();
        fileManager = f;
        scrollBarHandler = s;

        undoStack = new Stack<Action>();
        redoStack = new Stack<Action>();

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void moveCursor(Node n) {
        cursor.moveTo(n);
        cursor.render(windowWidth, windowHeight);
    }

    public void changeWindowWidth(int width) {
        windowWidth = width;
    }

    public void changeWindowHeight(int height) {
        windowHeight = height;
    }

    public void render() {
        textBuffer.render(windowWidth, windowHeight);
        cursor.render(windowWidth, windowHeight);
    }

    public void stackUndo(Action action) {
        if (undoStack.size() >= 100) {
            undoStack.removeElementAt(0);
        }
        undoStack.push(action);
        redoStack.clear();
    }


    public void undo() {
        if(undoStack.empty()) {
            return;
        }
        Action action = undoStack.pop();
        if (action.getTypeOfAction().equals("insert")) {
            cursor.moveTo(action.getContent());
            cursor.remove();
        } else {
            cursor.moveTo(action.getActionPosition());
            cursor.insert(action.getContent());
        }
        render();
        redoStack.push(action);
    }

    public void redo() {
        if(redoStack.empty()) {
            return;
        }
        Action action = redoStack.pop();
        if (action.getTypeOfAction().equals("insert")) {
            cursor.moveTo(action.getActionPosition());
            cursor.insert(action.getContent());
        } else {
            cursor.moveTo(action.getContent());
            cursor.remove();
        }
        render();
        undoStack.push(action);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_TYPED && !keyEvent.isShortcutDown()) {
            // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
            // the KEY_TYPED event, javafx handles the "Shift" key and associated
            // capitalization.
            String characterTyped = keyEvent.getCharacter();
            // Makes the scrollbar snap to where the cursor can be seen during typing
            if (cursor.getRenderPosYBottom() - scrollBarHandler.getChange() > windowHeight || cursor.getRenderPosY() - scrollBarHandler.getChange() < 0) {
                scrollBarHandler.snapCursor(cursor);
            }
            if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                // Ignore control keys, which have non-zero length, as well as the backspace
                // key, which is represented as a character of value = 8 on Windows.
                Node insertPosition = cursor.getNode();
                cursor.insert(characterTyped);
                stackUndo(new Action("insert", insertPosition, cursor.getNode()));
                render();
                keyEvent.consume();
            } else if(characterTyped.charAt(0) == 8) {
                Node s = cursor.remove();
                if (s != null) {
                    stackUndo(new Action("remove", cursor.getNode(), s));
                }
                render();
                keyEvent.consume();
            }


        } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
            // events have a code that we can check (KEY_TYPED events don't have an associated
            // KeyCode).
            KeyCode code = keyEvent.getCode();
            if ((code == KeyCode.EQUALS || code == KeyCode.PLUS) && keyEvent.isShortcutDown()) {
                fontSize += 4;
                textBuffer.setFont(Font.font(fontName, fontSize));
                cursor.setFont(Font.font(fontName, fontSize), windowWidth, windowHeight);
            } else if (code == KeyCode.MINUS && keyEvent.isShortcutDown()) {
                fontSize = Math.max(4, fontSize - 4);
                textBuffer.setFont(Font.font(fontName, fontSize));
                cursor.setFont(Font.font(fontName, fontSize), windowWidth, windowHeight);
            } else  if (code == KeyCode.LEFT) {
                cursor.moveLeft();
                cursor.render(windowWidth, windowHeight);
            } else if(code == KeyCode.RIGHT) {
                cursor.moveRight();
                cursor.render(windowWidth, windowHeight);
            } else if(code == KeyCode.P && keyEvent.isShortcutDown()) {
                System.out.println(cursor.getPosition());
            } else if(code == KeyCode.S && keyEvent.isShortcutDown()) {
                fileManager.save();
            } else if(code == KeyCode.DOWN) {
                adjustCursor(cursor.getRenderPosX(), cursor.getRenderPosYBottom() + 1);
                if (cursor.getRenderPosYBottom() - scrollBarHandler.getChange() > windowHeight || cursor.getRenderPosY() - scrollBarHandler.getChange() < 0) {
                    scrollBarHandler.snapCursor(cursor);
                }
            } else if(code == KeyCode.UP) {
                adjustCursor(cursor.getRenderPosX(), cursor.getRenderPosY() - 1);
                if (cursor.getRenderPosYBottom() - scrollBarHandler.getChange() > windowHeight || cursor.getRenderPosY() - scrollBarHandler.getChange() < 0) {
                    scrollBarHandler.snapCursor(cursor);
                }
            } else if(code == KeyCode.Z && keyEvent.isShortcutDown()) {
                undo();
            } else if(code == KeyCode.Y && keyEvent.isShortcutDown()) {
                redo();
            }
        }
    }



    public void adjustCursor(double x, double y) {
        //need to check that everything is still on the same line before searching for closest
        Node node = searchVertical(y);

        if (node.item == null) {
            return;
        }

        final double lineYPos = ((Text) node.item).getY();

        while (node.item != null && ((Text) node.item).getY() == lineYPos) {
            if (((Text) node.item).getX() + ((Text) node.item).getLayoutBounds().getWidth()/2 >= x) {
                moveCursor(node.previous);
                return;
            }
            node = node.next;
        }

        moveCursor(node.previous);
    }

    public Node searchVertical(double y) {
        ArrayList<NewLinePosition> linePositions = textBuffer.getLinePositions();

        if (y < linePositions.get(0).getPositionOfTopLeftCorner()) {
            return linePositions.get(0).getFirstNodeInLine();
        } else if (y > linePositions.get(linePositions.size() - 1).getPositionOfTopLeftCorner()) {
            return linePositions.get(linePositions.size() - 1).getFirstNodeInLine();
        }

        for(int i = 0; i < linePositions.size() - 1; i++) {
            NewLinePosition newLinePosition = linePositions.get(i);
            NewLinePosition nextNewLinePosition = linePositions.get(i + 1);
            double lowerBound = newLinePosition.getPositionOfTopLeftCorner();
            double upperBound = nextNewLinePosition.getPositionOfTopLeftCorner();
            if (lowerBound <= y && y < upperBound) {
                return newLinePosition.getFirstNodeInLine();
            }
        }
        throw new RuntimeException("search vertical returns an unexpected value");
    }
}
