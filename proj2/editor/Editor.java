package editor;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Handles the GUI
 */
public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    private int windowWidth = WINDOW_WIDTH;
    private int windowHeight = WINDOW_HEIGHT;

    private Cursor cursor;
    private TextContainer textBuffer;
    private Group root;
    private Group textRoot;
    private ScrollBarHandler scroller;
    private Scene scene;
    private ResizeHandler resizeHandler;

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        root = new Group();
        textRoot = new Group();
        root.getChildren().add(textRoot);
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        scroller = new ScrollBarHandler(root, textRoot, WINDOW_WIDTH, WINDOW_HEIGHT);

        //initializes the cursor and textcontainer for the handlers to use
        textBuffer = new TextContainer(textRoot, scroller);
        cursor = new Cursor(textRoot, textBuffer);
        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(WINDOW_WIDTH, WINDOW_HEIGHT, textBuffer, cursor);
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);

        MouseEventHandler mouseEventHandler = new MouseEventHandler(cursor, textBuffer, keyEventHandler);
        scene.setOnMousePressed(mouseEventHandler);
        scene.setOnMouseDragged(mouseEventHandler);
        scene.setOnMouseReleased(mouseEventHandler);

        resizeHandler = new ResizeHandler(scene, (KeyEventHandler) keyEventHandler, scroller);

        primaryStage.setTitle("Editor");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
