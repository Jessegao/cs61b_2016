
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/* Maven is used to pull in these dependencies. */
import com.google.gson.Gson;

import javax.imageio.ImageIO;

import static spark.Spark.*;

/**
 * This MapServer class is the entry point for running the JavaSpark web server for the BearMaps
 * application project, receiving API calls, handling the API call processing, and generating
 * requested images and routes.
 *
 * @author Alan Yao
 */
public class MapServer {

    /**
     * This is the QuadTree that handles stuff
     */
    private static QuadTree quadTree;

    /**
     * The root upper left/lower right longitudes and latitudes represent the bounding box of
     * the root tile, as the images in the img/ folder are scraped.
     * Longitude == x-axis; latitude == y-axis.
     */
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    /**
     * Each tile is 256x256 pixels.
     */
    public static final int TILE_SIZE = 256;
    /**
     * HTTP failed response.
     */
    private static final int HALT_RESPONSE = 403;
    /**
     * Route stroke information: typically roads are not more than 5px wide.
     */
    public static final float ROUTE_STROKE_WIDTH_PX = 5.0f;
    /**
     * Route stroke information: Cyan with half transparency.
     */
    public static final Color ROUTE_STROKE_COLOR = new Color(108, 181, 230, 200);
    /**
     * The tile images are in the IMG_ROOT folder.
     */
    private static final String IMG_ROOT = "img/";
    /**
     * The OSM XML file path. Downloaded from <a href="http://download.bbbike.org/osm/">here</a>
     * using custom region selection.
     **/
    private static final String OSM_DB_PATH = "berkeley.osm";
    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside getMapRaster(). <br>
     * ullat -> upper left corner latitude,<br> ullon -> upper left corner longitude, <br>
     * lrlat -> lower right corner latitude,<br> lrlon -> lower right corner longitude <br>
     * w -> user viewport window width in pixels,<br> h -> user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat",
        "ullon", "lrlat", "lrlon", "w", "h"};
    /**
     * Each route request to the server will have the following parameters
     * as keys in the params map.<br>
     * start_lat -> start point latitude,<br> start_lon -> start point longitude,<br>
     * end_lat -> end point latitude, <br>end_lon -> end point longitude.
     **/
    private static final String[] REQUIRED_ROUTE_REQUEST_PARAMS = {"start_lat", "start_lon",
        "end_lat", "end_lon"};
    /* Define any static variables here. Do not define any instance variables of MapServer. */
    private static GraphDB g;
    private static LinkedList<Long> route;

    /**
     * Place any initialization statements that will be run before the server main loop here.
     * Do not place it in the main function. Do not place initialization code anywhere else.
     * This is for testing purposes, and you may fail tests otherwise.
     **/
    public static void initialize() {
        g = new GraphDB(OSM_DB_PATH);
        quadTree = new QuadTree(ROOT_ULLAT, ROOT_ULLON, ROOT_LRLAT, ROOT_LRLON, 0);
    }

    public static void main(String[] args) {
        initialize();
        staticFileLocation("/page");
        /* Allow for all origin requests (since this is not an authenticated server, we do not
         * care about CSRF).  */
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
        });

        /* Define the raster endpoint for HTTP GET requests. I use anonymous functions to define
         * the request handlers. */
        get("/raster", (req, res) -> {
            HashMap<String, Double> params =
                    getRequestParams(req, REQUIRED_RASTER_REQUEST_PARAMS);
            /* The png image is written to the ByteArrayOutputStream */
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            /* getMapRaster() does almost all the work for this API call */
            Map<String, Object> rasteredImgParams = getMapRaster(params, os);
            /* On an image query success, add the image data to the response */
            if (rasteredImgParams.containsKey("query_success")
                    && (Boolean) rasteredImgParams.get("query_success")) {
                String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
                rasteredImgParams.put("b64_encoded_image_data", encodedImage);
            }
            /* Encode response to Json */
            Gson gson = new Gson();
            return gson.toJson(rasteredImgParams);
        });

        /* Define the routing endpoint for HTTP GET requests. */
        get("/route", (req, res) -> {
            HashMap<String, Double> params =
                    getRequestParams(req, REQUIRED_ROUTE_REQUEST_PARAMS);
            LinkedList<Long> route1 = findAndSetRoute(params);
            return !route1.isEmpty();
        });

        /* Define the API endpoint for clearing the current route. */
        get("/clear_route", (req, res) -> {
            clearRoute();
            return true;
        });

        /* Define the API endpoint for search */
        get("/search", (req, res) -> {
            Set<String> reqParams = req.queryParams();
            String term = req.queryParams("term");
            Gson gson = new Gson();
            /* Search for actual location data. */
            if (reqParams.contains("full")) {
                List<Map<String, Object>> data = getLocations(term);
                return gson.toJson(data);
            } else {
                /* Search for prefix matching strings. */
                List<String> matches = getLocationsByPrefix(term);
                return gson.toJson(matches);
            }
        });

        /* Define map application redirect */
        get("/", (request, response) -> {
            response.redirect("/map.html", 301);
            return true;
        });
    }

    /**
     * Validate & return a parameter map of the required request parameters.
     * Requires that all input parameters are doubles.
     *
     * @param req            HTTP Request
     * @param requiredParams TestParams to validate
     * @return A populated map of input parameter to it's numerical value.
     */
    private static HashMap<String, Double> getRequestParams(
            spark.Request req, String[] requiredParams) {
        Set<String> reqParams = req.queryParams();
        HashMap<String, Double> params = new HashMap<>();
        for (String param : requiredParams) {
            if (!reqParams.contains(param)) {
                halt(HALT_RESPONSE, "Request failed - parameters missing.");
            } else {
                try {
                    params.put(param, Double.parseDouble(req.queryParams(param)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    halt(HALT_RESPONSE, "Incorrect parameters - provide numbers.");
                }
            }
        }
        return params;
    }


    /**
     * Handles raster API calls, queries for tiles and rasters the full image. <br>
     * <p>
     *     The rastered photo must have the following properties:
     *     <ul>
     *         <li>Has dimensions of at least w by h, where w and h are the user viewport width
     *         and height.</li>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *         <li>If a current route exists, lines of width ROUTE_STROKE_WIDTH_PX and of color
     *         ROUTE_STROKE_COLOR are drawn between all nodes on the route in the rastered photo.
     *         </li>
     *     </ul>
     *     Additional image about the raster is returned and is to be included in the Json response.
     * </p>
     * @param params Map of the HTTP GET request's query parameters - the query bounding box and
     *               the user viewport width and height.
     * @param os     An OutputStream that the resulting png image should be written to.
     * @return A map of parameters for the Json response as specified:
     * "raster_ul_lon" -> Double, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Double, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Double, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Double, the bounding lower right latitude of the rastered image <br>
     * "raster_width"  -> Double, the width of the rastered image <br>
     * "raster_height" -> Double, the height of the rastered image <br>
     * "depth"         -> Double, the 1-indexed quadtree depth of the nodes of the rastered image.
     * Can also be interpreted as the length of the numbers in the image string. <br>
     * "query_success" -> Boolean, whether an image was successfully rastered. <br>
     * @see #REQUIRED_RASTER_REQUEST_PARAMS
     */
    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside getMapRaster(). <br>
     * ullat -> upper left corner latitude,<br> ullon -> upper left corner longitude, <br>
     * lrlat -> lower right corner latitude,<br> lrlon -> lower right corner longitude <br>
     * w -> user viewport window width in pixels,<br> h -> user viewport height in pixels.
     **/
    public static Map<String, Object> getMapRaster(Map<String, Double> params, OutputStream os) {
        HashMap<String, Object> rasteredImageParams = new HashMap<>();

        ArrayList<QuadTreeNode> tileNodes =
                quadTree.getRasterImages(params.get("ullat"), params.get("ullon"),
                params.get("lrlat"), params.get("lrlon"), params.get("w"));

        BufferedImage bigImage = stitchImages(tileNodes);
        QuadTreeNode first = tileNodes.get(0);
        QuadTreeNode last = tileNodes.get(tileNodes.size() - 1);
        double dppLon = Math.abs(first.getUpperLeftLongitude()
                - last.getLowerRightLongitude()) / bigImage.getWidth();
        double dppLat = Math.abs(first.getUpperLeftLatitude()
                - last.getLowerRightLatitude()) / bigImage.getHeight();
        drawRoute(route, bigImage, dppLon, dppLat,
                first.getUpperLeftLongitude(), first.getUpperLeftLatitude());
        try {
            ImageIO.write(bigImage, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rasteredImageParams.put("raster_ul_lon", first.getUpperLeftLongitude());
        rasteredImageParams.put("raster_ul_lat", first.getUpperLeftLatitude());
        rasteredImageParams.put("raster_lr_lon", last.getLowerRightLongitude());
        rasteredImageParams.put("raster_lr_lat", last.getLowerRightLatitude());
        rasteredImageParams.put("raster_width", bigImage.getWidth());
        rasteredImageParams.put("raster_height", bigImage.getHeight());
        rasteredImageParams.put("depth", first.getDepth());
        rasteredImageParams.put("query_success", true);
        return rasteredImageParams;
    }

    private static String getProperFileName(QuadTreeNode node) {
        return IMG_ROOT + node.getPictureName() + ".png";
    }

    public static void drawRoute(LinkedList<Long> route1, BufferedImage image,
                                 double dppLon, double dppLat,
                                 double queryTopLeftLongitude,
                                 double queryTopLeftLatitude) {
        if (route1 == null) {
            return;
        }
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setStroke(new BasicStroke(MapServer.ROUTE_STROKE_WIDTH_PX,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.setColor(ROUTE_STROKE_COLOR);
        Node last = null;
        for (long id : route1) {
            String nodeID = String.valueOf(id);
            Node node = g.getNodeHashMap().get(nodeID);
            if (last != null) {
                int lon = (int) ((node.getLongitude() - queryTopLeftLongitude) / dppLon);
                int lat = (int) -((node.getLatitude() - queryTopLeftLatitude) / dppLat);
                int lastlon = (int) ((last.getLongitude() - queryTopLeftLongitude) / dppLon);
                int lastlat = (int) -((last.getLatitude() - queryTopLeftLatitude) / dppLat);
                graphics.drawLine(lastlon, lastlat, lon, lat);
            }
            last = node;
        }
    }

    private static BufferedImage stitchImages(ArrayList<QuadTreeNode> tileNodes) {
        Collections.sort(tileNodes);
        int row = numberOfTilesPerRow(tileNodes);
        int col = tileNodes.size() / row;
        BufferedImage bufferedImage = new BufferedImage(row * TILE_SIZE,
                col * TILE_SIZE, BufferedImage.TYPE_INT_RGB);

        Graphics graphics = bufferedImage.getGraphics();
        int x = 0;
        int y = 0;
        for (QuadTreeNode node : tileNodes) {
            BufferedImage bi;
            try {
                bi = ImageIO.read(new File(getProperFileName(node)));
            } catch (java.io.IOException exception) {
                throw new RuntimeException("Aye you messed up. A file doesn't exist.");
            }
            graphics.drawImage(bi, x, y, null);
            x += TILE_SIZE;
            if (x >= bufferedImage.getWidth()) {
                x = 0;
                y += bi.getHeight();
            }
        }
        return bufferedImage;
    }

    private static int numberOfTilesPerRow(ArrayList<QuadTreeNode> tileNodes) {
        if (tileNodes.size() == 0) {
            throw new RuntimeException("The list of tiles is empty");
        }
        int counter = 0;
        double latitude = tileNodes.get(0).getUpperLeftLatitude();
        for (QuadTreeNode node : tileNodes) {
            if (node.getUpperLeftLatitude() == latitude) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Searches for the shortest route satisfying the input request parameters, sets it to be the
     * current route, and returns a <code>LinkedList</code> of the route's node ids for testing
     * purposes. <br>
     * The route should start from the closest node to the start point and end at the closest node
     * to the endpoint. Distance is defined as the euclidean between two points (lon1, lat1) and
     * (lon2, lat2).
     *
     * @param params from the API call described in REQUIRED_ROUTE_REQUEST_PARAMS
     *               start_lat -> start point latitude,
     *               start_lon -> start point longitude,
     *               end_lat -> end point latitude,
     *               end_lon -> end point longitude.
     * @return A LinkedList of node ids from the start of the route to the end.
     */

    public static LinkedList<Long> findAndSetRoute(Map<String, Double> params) {
        LinkedList<Long> path = new LinkedList<Long>();
        Node startNode = getClosestNode(params.get("start_lat"), params.get("start_lon"));
        Node endNode = getClosestNode(params.get("end_lat"), params.get("end_lon"));

        PriorityQueue<Node> minPQ = new PriorityQueue<Node>();
        startNode.setDistanceFromStart(0);
        startNode.setDistanceToEnd(startNode.distanceTo(endNode));
        minPQ.add(startNode);
        findPath(endNode, minPQ);
        Node nodeIter = endNode;
        while (nodeIter != null) {
            path.add(0, Long.valueOf(nodeIter.getNodeID()));
            nodeIter = nodeIter.getPreviousNode();
        }
        route = path;
        return path;
    }

    private static Node findPath(Node endNode, PriorityQueue<Node> minPQ) {
        LinkedList<Node> nodes = new LinkedList<Node>();
        Node search = minPQ.remove();
        while (!search.getNodeID().equals(endNode.getNodeID())) {
            for (Node node : search.getAdjacentNodes()) {
                node.setDistanceToEnd(node.distanceTo(endNode));
                if (node.getDistanceFromStart() > search.getDistanceFromStart()
                        + search.distanceTo(node)) {
                    nodes.add(node);
                    node.setDistanceFromStart(search.getDistanceFromStart()
                            + node.distanceTo(search));
                    node.setPreviousNode(search);
                    minPQ.add(node);
                }
            }
            search = minPQ.remove();
        }
        for (Node node : nodes) {
            node.setDistanceFromStart(Double.MAX_VALUE);
        }
        return search;
    }

    private static Node getClosestNode(double latitude, double longitude) {
        // never gonna have an empty hashmap
        double minDistance = Double.MAX_VALUE;
        Node min = null;
        for (Node node : g.getNodeHashMap().values()) {
            double dist = getDistance(latitude, node.getLatitude(),
                    longitude, node.getLongitude());
            if (dist < minDistance) {
                min = node;
                minDistance = dist;
            }
        }
        return min;
    }

    private static double getDistance(double lat1, double lat2, double lon1, double lon2) {
        return Math.sqrt(((lat1 - lat2) * (lat1 - lat2)) + ((lon1 - lon2) * (lon1 - lon2)));
    }

    /**
     * Clear the current found route, if it exists.
     */
    public static void clearRoute() {
        route = null;
    }

    /**
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     *
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public static List<String> getLocationsByPrefix(String prefix) {
        return new LinkedList<>();
    }

    /**
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     *
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public static List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }
}
