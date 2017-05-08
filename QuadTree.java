import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by cs61bl-gv on 7/14/16.
 */
public class QuadTree {


    public static final Pt UPPERZERO = new Pt(MapServer.ROOT_ULLON, MapServer.ROOT_ULLAT);
    public static final Pt LOWERZERO = new Pt(MapServer.ROOT_LRLON, MapServer.ROOT_LRLAT);
    //private static QuadTree images = create();
    protected QTreeNode root;

    public QuadTree() {
        root = null;
    }

    /* Constructs a binary tree with root T. */
    public QuadTree(QTreeNode t) {
        root = t;
    }


    public void print() {
        if (root != null) {
            root.print(0);
        }
        System.out.println();
    }

    public double getLevel(double ddp) {
        return root.getLevel(ddp);
    }

    public LinkedList<QTreeNode> collectNodes(RectanglePrecise window, double lvl) {

        LinkedList<QTreeNode> collectedNodes = root.createLevelList(lvl);
        LinkedList<QTreeNode> adjustedCollection = new LinkedList<>();
        adjustedCollection.addAll(collectedNodes);
        LinkedList<QTreeNode> finalNodes = new LinkedList<>();

        //       window.height += .5;

        for (QTreeNode q : collectedNodes) {
//            System.out.println(q.area.toString());
            if (window.contains(q.area)) {
                continue;
            }
            if (window.intersects(q.area)) {
                continue;
            }
            adjustedCollection.remove(q);


        }

        adjustedCollection = sortFilenames(adjustedCollection);

        for (QTreeNode q : adjustedCollection) {
            finalNodes.add(q);
            // System.out.println(q.filename);
        }
        //  System.out.println();
        // System.out.println("---------------------------------");
        // System.out.println();

        return finalNodes;
    }


    public LinkedList<QTreeNode> sortFilenames(LinkedList<QTreeNode> files) {

        LinkedList<QTreeNode> sorted = new LinkedList<>();

        for (QTreeNode q : files) {
            sorted.add(q);
        }

        Collections.sort(sorted);
        Collections.sort(sorted);
        Collections.sort(sorted);
        Collections.sort(sorted);

        return sorted;


    }

    public LinkedList<QTreeNode> findNextQTN(LinkedList<QTreeNode> files,
                                             LinkedList<QTreeNode> compare) {
        LinkedList<QTreeNode> temp = files;
        LinkedList<QTreeNode> sorts = compare;
        int count = 0;
        QTreeNode answer = null;


        for (int i = 0; i < files.size(); i++) {
            if (!compare.contains(files.get(i))) {
                answer = files.get(i);
                for (int j = i; i < files.size() - i; i++) {
                    if (answer.upp.yy == files.get(i).upp.yy) {
                        sorts = findNextQTN(files, compare);
                    }
                }

            }

        }
        return sorts;
    }
}
