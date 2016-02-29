
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
    private Group root;

    private static final int STARTING_FONT_SIZE = 20;
    private static final int STARTING_TEXT_POSITION_X = 250;
    private static final int STARTING_TEXT_POSITION_Y = 250;

    private int fontSize = STARTING_FONT_SIZE;

    private String fontName = "Verdana";

    //remember to call render after every change made

    public KeyEventHandler(final Group root, int windowWidth, int windowHeight) {

        this.root = root;
        // Initialize some empty text and add it to root so that it will be displayed.
        textBuffer = new TextContainer();
        textBuffer.setFont(Font.font(fontName, fontSize));

        // All new Nodes need to be added to the root in order to be displayed.
        textBuffer.render(root, windowWidth, windowHeight);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
            // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
            // the KEY_TYPED event, javafx handles the "Shift" key and associated
            // capitalization.
            String characterTyped = keyEvent.getCharacter();
            if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                // Ignore control keys, which have non-zero length, as well as the backspace
                // key, which is represented as a character of value = 8 on Windows.
                textBuffer.insert(characterTyped); //IMPORTANT remember to implement the cursor thing
                keyEvent.consume();
            }
            textBuffer.render(root);
        } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
            // events have a code that we can check (KEY_TYPED events don't have an associated
            // KeyCode).
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.UP) {
                fontSize += 5;
                textBuffer.setFont(Font.font(fontName, fontSize));
            } else if (code == KeyCode.DOWN) {
                fontSize = Math.max(0, fontSize - 5);
                textBuffer.setFont(Font.font(fontName, fontSize));
            }
        }
    }
}
