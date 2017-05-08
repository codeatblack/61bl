import java.util.LinkedList;

/**
 * Created by cs61bl-gv on 7/17/16.
 */
public class QTreeNode implements Comparable<QTreeNode> {

    private final int imgSize = MapServer.TILE_SIZE;
    String root;
    double level;
    QTreeNode a;
    QTreeNode b;
    QTreeNode c;
    QTreeNode d;
    Pt upp;
    Pt low;
    String filename;
    double ddp;
    RectanglePrecise area;
    LinkedList<MapNode> nodesWithin = new LinkedList<>();

    public QTreeNode(String s) {
        this.root = s;
        this.upp = new Pt(0, 0);
        this.low = new Pt(0, 0);
        if (s.equals("0")) {
            this.level = 0.0;
            this.a = new QTreeNode("1");
            this.b = new QTreeNode("2");
            this.c = new QTreeNode("3");
            this.d = new QTreeNode("4");
        } else {
            this.a = this.b = this.c = this.d = null;
            level = 1.0 * s.length();
            setChildren();
        }
        this.filename = s + ".png";
    }

    @Override
    public int compareTo(QTreeNode next) {

        if (upp.iisAbove(next.upp)) {
            return -1;
        } else if (upp.iisBelow(next.upp)) {
            return 1;
        } else {
            if (upp.iisToTheLeftOf(next.upp)) {
                return -1;
            } else if (upp.iisToTheRightOf(next.upp)) {
                return 1;
            }
        }
        return 0;
    }

    public void setChildren() {
        if (level < 7) {
            a = new QTreeNode(root + "1");
            b = new QTreeNode(root + "2");
            c = new QTreeNode(root + "3");
            d = new QTreeNode(root + "4");
        }
    }

    public void setPts(Pt upper, Pt lower, GraphDB g) {

        if (level < 7) {

            a.upp = new Pt(upper.xx, upper.yy);
            a.low = new Pt(upper.xx + ((lower.xx - upper.xx) / 2.0),
                    lower.yy + (upper.yy - lower.yy) / 2.0);

            b.upp = new Pt(a.upp.xx + (lower.xx - upper.xx) / 2.0, a.upp.yy);
            b.low = new Pt(a.low.xx + (lower.xx - upper.xx) / 2.0, a.low.yy);

            c.upp = new Pt(a.upp.xx, (upper.yy - lower.yy) / 2.0 + lower.yy);
            c.low = new Pt(b.upp.xx, lower.yy);

            d.upp = new Pt(upper.xx + (lower.xx - upper.xx) / 2.0,
                    lower.yy + (upper.yy - lower.yy) / 2.0);
            d.low = new Pt(lower.xx, lower.yy);

            a.setPts(a.upp, a.low, g);
            b.setPts(b.upp, b.low, g);
            c.setPts(c.upp, c.low, g);
            d.setPts(d.upp, d.low, g);

        }

        ddp = (low.xx - upp.xx) / 256.0;
        area = new RectanglePrecise(upp.xx, upp.yy, low.xx, low.yy);
//        for (MapNode m : g.mapNodes().values()){
//            if (area.contains(m.pt())){
//                nodesWithin.add(m);
//            }
//        }

    }

    public double getLevel(double quer) {

        while (level < 7.0) {
            if (quer >= a.ddp) {
                return a.level;
            } else {
                return a.getLevel(quer);
            }
        }

        return level;
    }

    public void print(int indentation) {
        System.out.println();
        for (int i = 0; i < indentation; i += 1) {
            System.out.print("    ");
        }
        System.out.print(root);
        System.out.println();
        if (low != null) {
            for (int i = 0; i < indentation; i += 1) {
                System.out.print("    ");
            }
            System.out.println("    The upper point is " + upp.toString());
            for (int i = 0; i < indentation; i += 1) {
                System.out.print("    ");
            }
            System.out.println("    The lower point is " + low.toString());
            for (int i = 0; i < indentation; i += 1) {
                System.out.print("    ");
            }
            System.out.println("    The distance per pixel is " + ddp);
        }
        if (level < 7) {
            a.print(indentation + 1);
            b.print(indentation + 1);
            c.print(indentation + 1);
            d.print(indentation + 1);
        }
    }


    public LinkedList<QTreeNode> createLevelList(double l) {
        LinkedList<QTreeNode> levelList = new LinkedList<>();
        if (level < 8) {
            if (level == l) {
                levelList.add(this);
            } else {
                levelList.addAll(a.createLevelList(l));
                levelList.addAll(b.createLevelList(l));
                levelList.addAll(c.createLevelList(l));
                levelList.addAll(d.createLevelList(l));
            }

        }


        return levelList;
    }
}
