import java.util.LinkedList;

/**
 * Created by cs61bl-gv on 8/3/16.
 */
public class MapNode implements Comparable<MapNode> {

    private long id;
    private Double lon;
    private Double lat;
    private Pt pt;

    public String name() {
        return name;
    }

    private String name;

    public int intid() {
        return intid;
    }

    private int intid;
    private boolean isConnected;
    private LinkedList<MapNode> connections;
    private Double aStar;
    private Double dfs;
    private Double dte;
    private MapNode parent;


    public MapNode(long id, Double lon, Double lat, String name, int intid) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.pt = new Pt(lon, lat);
        this.name = name;
        this.isConnected = false;
        this.connections = new LinkedList<>();
        this.dfs = 100000000000000000.0;
        this.dte = 100000000000000000.0;
        this.aStar = 300000000000000000.0;
        this.parent = this;
        this.intid = intid;
    }

    public MapNode(Pt pt) {
        this.id = 5;
        this.lon = pt.xx;
        this.lat = pt.yy;
        this.pt = pt;
        this.isConnected = false;
        this.connections = new LinkedList<>();
        this.dfs = 100000000000000000.0;
        this.dte = 100000000000000000.0;
        this.aStar = 300000000000000000.0;
        this.parent = this;
    }


    public long id() {
        return id;
    }

    public Double lon() {
        return lon;
    }

    public Double lat() {
        return lat;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public LinkedList<MapNode> connections() {
        return connections;
    }

    public void addToConnections(MapNode m) {
        connections.add(m);
        m.connections.add(this);
        m.isConnected = true;
        isConnected = true;
    }

    public String toString() {
        return "(" + lon + ", " + lat + ")";
    }

    public int compareTo(MapNode o) {
        return Double.compare(aStar, o.aStar);
    }

    public void setDfs(Double dfs) {
        this.dfs = dfs;
        this.aStar = this.dfs + this.dte;
    }

    public void setDte(Double dte) {
        this.dte = dte;
        this.aStar = this.dfs + this.dte;
    }


    public void setParent(MapNode parent) {
        this.parent = parent;
    }

    public void setVals(Double dfser, Double dteer, MapNode parenter) {
        this.dfs = dfser;
        this.dte = dteer;
        this.aStar = dfser + dteer;
        this.parent = parenter;
    }

    public Double dfs() {
        return dfs;
    }

    public MapNode parent() {
        return parent;
    }

}
