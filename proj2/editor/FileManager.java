package editor;

import java.io.*;
import java.util.List;
import javafx.scene.text.Text;

/**
 * Created by Jesse on 3/6/2016.
 */
public class FileManager {

    private String inputFilename;
    private Cursor cursor;
    FileWriter writer;
    TextContainer textContainer;

    public FileManager(List<String> args, Cursor c, TextContainer t) {
        textContainer = t;
        cursor = c;
        inputFilename = args.get(0);
        FileReader reader;
        BufferedReader bufferedReader;

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
            while ((intRead = bufferedReader.read()) != intRead) {
                // The integer read can be cast to a char, because we're assuming ASCII.
                char charRead = (char) intRead;
                cursor.insert(String.valueOf(charRead));
            }
            //moves cursor to beginning of the file
            cursor.moveTo(textContainer.getFirst());
            // Close the reader.
            bufferedReader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
            System.exit(1);
        } catch (IOException ioException) {
            System.out.println("Error when reading file; exception was: " + ioException);
            System.exit(1);
        }
    }

    public void save() {
        try {
            System.out.println("printing");
            File inputFile = new File(inputFilename);
            if (!inputFile.exists()) {
                inputFile.createNewFile();
            }
            writer = new FileWriter(inputFile);
            Node<Text> node = textContainer.getFirst().next;
            while (node.item != null) {
                writer.write(node.item.getText().charAt(0));
                node = node.next;
            }
        } catch (IOException ioException) {
            System.out.println("Error when writing to file; exception was: " + ioException);
            System.exit(1);
        }
    }

}
