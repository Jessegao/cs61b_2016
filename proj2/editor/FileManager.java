package editor;

import java.io.*;
import java.util.List;
import javafx.scene.text.Text;

/**
 * Created by Jesse on 3/6/2016.
 */
public class FileManager {

    private String inputFilename;
    private String outputFilename;
    private Cursor cursor;
    FileWriter writer;
    TextContainer textContainer;

    public FileManager(List<String> args, Cursor c, TextContainer t) {
        textContainer = t;
        cursor = c;
        inputFilename = args.get(0);
        outputFilename = args.get(1);
        FileReader reader;
        BufferedReader bufferedReader;

        try {
            File inputFile = new File(inputFilename);
            // Check to make sure that the input file exists!
            if (!inputFile.exists()) {
                System.out.println("Unable to open file, " + inputFilename
                        + " does not exist");
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

            // Close the reader.
            bufferedReader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("Error when writing; exception was: " + ioException);
        }
    }

    public void save() {
        try {
            // Create a FileWriter to write to outputFilename. FileWriter will overwrite any data
            // already in outputFilename.
            writer = new FileWriter(outputFilename);
            Node<Text> node = textContainer.getFirst().next;
            while (node.item != null) {
                writer.write(node.item.getText());
                node = node.next;
            }
        } catch (IOException ioException) {
            System.out.println("Error when writing; exception was: " + ioException);
        }
    }

}
