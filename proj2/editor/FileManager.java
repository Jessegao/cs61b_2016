package editor;

import java.io.*;
import java.util.List;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by Jesse on 3/6/2016.
 */
public class FileManager {

    private String inputFilename;
    private Cursor cursor;
    FileWriter writer;
    TextContainer textContainer;
    Editor editor;

    private static final int STARTING_FONT_SIZE = 12;

    private String fontName = "Verdana";

    public FileManager(List<String> args, Cursor c, TextContainer t, Editor editor) {
        textContainer = t;
        cursor = c;
        inputFilename = args.get(0);
        FileReader reader;
        BufferedReader bufferedReader;
        textContainer.setFont(new Font(fontName, STARTING_FONT_SIZE));

        try {
            File inputFile = new File(inputFilename);
            // Check to make sure that the input file exists!
            if (!inputFile.exists()) {
                return;
            }

            reader = new FileReader(inputFile);
            bufferedReader = new BufferedReader(reader);

            int intRead = -1;
            // Keep reading from the file input read() returns -1, which means the end of the file
            // was reached.
            while ((intRead = bufferedReader.read()) != -1) {
                // The integer read can be cast to a char, because we're assuming ASCII.
                char charRead = (char) intRead;
                cursor.insert(String.valueOf(charRead));
            }
            textContainer.render(editor.getWindowWidth(), editor.getWindowHeight());
            //moves cursor to beginning of the file
            cursor.moveTo(textContainer.getFirst());
            // Close the reader.
            bufferedReader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
            System.exit(1);
        } catch (IOException ioException) {
            System.out.println("Error when writing; exception was: " + ioException);
            System.exit(1);
        }
    }

    public void save() {
        try {
            writer = new FileWriter(inputFilename);
            Node<Text> node = textContainer.getFirst().next;
            while (node.item != null) {
                writer.write(node.item.getText());
                node = node.next;
            }
            writer.close();
        } catch (IOException ioException) {
            System.out.println("Error when writing; exception was: " + ioException);
            System.exit(1);
        }
    }

}
