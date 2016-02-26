
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

import java.util.ArrayList;

/** An EventHandler to handle keys that get pressed. */
public class KeyEventHandler implements EventHandler<KeyEvent> {
    int textCenterX;
    int textCenterY;
    private int cursorPosition;
    private ArrayList<Text>

    private static final int STARTING_FONT_SIZE = 20;
    private static final int STARTING_TEXT_POSITION_X = 250;
    private static final int STARTING_TEXT_POSITION_Y = 250;

    /** The Text to display on the screen. */
    private Text displayText = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, "");
    private int fontSize = STARTING_FONT_SIZE;

    private String fontName = "Verdana";

    public KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
        textCenterX = windowWidth / 2;
        textCenterY = windowHeight / 2;

        // Initialize some empty text and add it to root so that it will be displayed.
        displayText = new Text(textCenterX, textCenterY, "");
        // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
        // that when the text is assigned a y-position, that position corresponds to the
        // highest position across all letters (for example, the top of a letter like "I", as
        // opposed to the top of a letter like "e"), which makes calculating positions much
        // simpler!
        displayText.setTextOrigin(VPos.TOP);
        displayText.setFont(Font.font(fontName, fontSize));

        // All new Nodes need to be added to the root in order to be displayed.
        root.getChildren().add(displayText);
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
                displayText.setText(characterTyped);
                keyEvent.consume();
            }

            centerText();
        } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
            // events have a code that we can check (KEY_TYPED events don't have an associated
            // KeyCode).
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.UP) {
                fontSize += 5;
                displayText.setFont(Font.font(fontName, fontSize));
                centerText();
            } else if (code == KeyCode.DOWN) {
                fontSize = Math.max(0, fontSize - 5);
                displayText.setFont(Font.font(fontName, fontSize));
                centerText();
            }
        }
    }

    private void centerText() {
        // Figure out the size of the current text.
        double textHeight = displayText.getLayoutBounds().getHeight();
        double textWidth = displayText.getLayoutBounds().getWidth();

        // Calculate the position so that the text will be centered on the screen.
        double textTop = textCenterY - textHeight / 2;
        double textLeft = textCenterX - textWidth / 2;

        // Re-position the text.
        displayText.setX(textLeft);
        displayText.setY(textTop);

        // Make sure the text appears in front of any other objects you might add.
        displayText.toFront();
    }
}
