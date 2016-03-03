
/**
 * Created by jesse on 2/25/16.
 * Doesn't use the TextContainer Class yet
 */
package editor;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** An EventHandler to handle keys that get pressed. */
public class KeyEventHandler implements EventHandler<KeyEvent> {

    private int cursorPosition;
    /** The Text to display on the screen. */
    private TextContainer textBuffer;
    private Cursor cursor;
    private Group root;

    private int winWidth;
    private int winHeight;

    private static final int STARTING_FONT_SIZE = 12;

    private int fontSize = STARTING_FONT_SIZE;

    private String fontName = "Verdana";

    //remember to call render after every change made

    public KeyEventHandler(final Group root, int windowWidth, int windowHeight, TextContainer t, Cursor c) {

        this.root = root;
        // Initialize some empty text and add it to root so that it will be displayed.
        textBuffer = t;
        textBuffer.setFont(Font.font(fontName, fontSize));
        // All new Nodes need to be added to the root in order to be displayed.
        textBuffer.render(windowWidth, windowHeight);
        cursor = c;
        //make cursor appear and blink
        cursor.firstRender();
        cursor.blink();

        winWidth = windowWidth;
        winHeight = windowHeight;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_TYPED && !keyEvent.isShortcutDown()) {
            // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
            // the KEY_TYPED event, javafx handles the "Shift" key and associated
            // capitalization.
            String characterTyped = keyEvent.getCharacter();
            if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                // Ignore control keys, which have non-zero length, as well as the backspace
                // key, which is represented as a character of value = 8 on Windows.
                cursor.insert(characterTyped);
                textBuffer.render(winWidth, winHeight);
                cursor.render();
                keyEvent.consume();
            } else if(characterTyped.charAt(0) == 8) {
                cursor.remove();
                textBuffer.render(winWidth, winHeight);
                cursor.render();
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
                cursor.setFont(Font.font(fontName, fontSize));
            } else if (code == KeyCode.MINUS && keyEvent.isShortcutDown()) {
                fontSize = Math.max(0, fontSize - 4);
                textBuffer.setFont(Font.font(fontName, fontSize));
                cursor.setFont(Font.font(fontName, fontSize));
            } else  if (code == KeyCode.LEFT) {
                cursor.moveLeft();
                cursor.render();
            } else if(code == KeyCode.RIGHT) {
                cursor.moveRight();
                cursor.render();
            } else if(code == KeyCode.P && keyEvent.isShortcutDown()) {
                System.out.println(cursor.getPosition());
            }
        }
    }
}
