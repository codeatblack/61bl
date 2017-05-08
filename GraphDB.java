import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Wraps the parsing functionality of the MapDBHandler as an example.
 * You may choose to add to the functionality of this class if you wish.
 *
 * @author Alan Yao
 */
public class GraphDB {

    Trie autocomplete = new Trie();
    private HashMap<Long, MapNode> mapNodes = new HashMap<>();
    private ArrayList<Long> shortestPath;

    /**
     * Example constructor shows how to create and start an XML parser.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapDBHandler maphandler = new MapDBHandler(this);
            saxParser.parse(inputFile, maphandler);
            autocomplete = maphandler.autocomplete;
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

    public HashMap<Long, MapNode> mapNodes() {
        return mapNodes;
    }

    public void addNode(MapNode m) {
        mapNodes.put(m.id(), m);
    }

    public void addConnection(MapNode m1, MapNode m2) {
        mapNodes.get(m1.id()).addToConnections(m2);
    }

    public void setShortestPath(ArrayList<Long> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public ArrayList<Long> shortestPath(MapNode m1, MapNode m2) {

        shortestPath = new ArrayList<>(1);

        PriorityQueue<MapNode> fringe = new PriorityQueue<>();
        HashSet<MapNode> visited = new HashSet<>();
        HashSet<MapNode> fakeFringe = new HashSet<>();

        m1.setVals(0.0, calcDistance(m1, m2), m1);
        fringe.add(m1);
        fakeFringe.add(m1);

        while (!fringe.isEmpty()) {
            MapNode lookingAt = fringe.poll();
            fakeFringe.remove(lookingAt);
            if (lookingAt.equals(m2)) {
                MapNode tracer = m2;
                while (!tracer.equals(m1)) {
                    shortestPath.add(0, tracer.parent().id());
                    tracer = tracer.parent();
                }
                shortestPath.add(0, m1.id());
                shortestPath.add(m2.id());
                shortestPath.remove(0);
                break;
            } else {
                visited.add(lookingAt);
                for (MapNode child : lookingAt.connections()) {
                    if (!visited.contains(child)) {
                        Double distanceFromStartThroughLookingAt
                                = lookingAt.dfs() + calcDistance(child, lookingAt);
                        if (!fakeFringe.contains(child)) {
                            child.setVals(distanceFromStartThroughLookingAt,
                                    calcDistance(child, m2), lookingAt);
                            fringe.add(child);
                            fakeFringe.add(child);
                        } else {
                            if (child.dfs() > distanceFromStartThroughLookingAt) {
                                child.setParent(lookingAt);
                                child.setDte(calcDistance(child, m2));
                                child.setDfs(distanceFromStartThroughLookingAt);
                            }
                        }
                    }
                }
            }
        }
        return shortestPath;
    }

    public Double calcDistance(MapNode m1, MapNode m2) {
        return Math.sqrt(Double.sum(Math.pow(m1.lon() - m2.lon(), 2.0),
                Math.pow(m1.lat() - m2.lat(), 2.0)));
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        LinkedList<MapNode> values = new LinkedList<>();
        for (Long s : mapNodes.keySet()) {
            values.add(mapNodes.get(s));
        }
        for (MapNode m : values) {
            if (!m.isConnected()) {
                mapNodes.remove(m.id());
            }
        }
    }
}
