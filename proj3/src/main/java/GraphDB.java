import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Wraps the parsing functionality of the MapDBHandler as an example.
 * You may choose to add to the functionality of this class if you wish.
 *
 * @author Alan Yao
 */
public class GraphDB {

    // A list of nodes to be initialized in the handler
    HashMap<String, Node> nodeHashMap;

    public HashMap<String, Node> getNodeHashMap() {
        return nodeHashMap;
    }

    /**
     * Example constructor shows how to create and start an XML parser.
     *
     * @param db_path Path to the XML file to be parsed.
     */
    public GraphDB(String dbpath) {
        try {
            nodeHashMap = new HashMap<String, Node>();
            File inputFile = new File(dbpath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapDBHandler maphandler = new MapDBHandler(this);
            saxParser.parse(inputFile, maphandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        LinkedList<String> toBeRemoved = new LinkedList<>();
        for (HashMap.Entry<String, Node> entry : nodeHashMap.entrySet()) {
            if (entry.getValue().hasNoAdjacentNodes()) {
                toBeRemoved.add(entry.getKey());
            }
        }
        for (String s : toBeRemoved) {
            nodeHashMap.remove(s);
        }
    }


}
