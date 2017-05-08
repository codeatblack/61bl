/**
 * Created by cs61bl-gv on 7/19/16.
 */

//thanks java! <3 rectangles
//source from http://grepcode.com/file/repository.grepcode.com/java/
// root/jdk/openjdk/6-b14/java/awt/Rectangle.java

public class RectanglePrecise {

    double upperX;
    double upperY;
    double lowerX;
    double lowerY;
    Pt upperPt;
    Pt lowerPt;

    public RectanglePrecise(double upperX, double upperY, double lowerX, double lowerY) {
        this.upperX = upperX;
        this.upperY = upperY;
        this.lowerX = lowerX;
        this.lowerY = lowerY;
        this.upperPt = new Pt(upperX, upperY);
        this.lowerPt = new Pt(lowerX, lowerY);
    }

    public RectanglePrecise(Pt p1, Pt p2) {
        if (p1.isAbove(p2)) {
            this.upperY = p1.yy;
            this.lowerY = p2.yy;
        } else {
            this.upperY = p2.yy;
            this.lowerY = p1.yy;
        }
        if (p1.isToTheLeftOf(p2)) {
            this.upperX = p1.xx;
            this.lowerX = p2.xx;
        } else {
            this.upperX = p2.xx;
            this.lowerX = p1.xx;
        }
        this.upperPt = new Pt(upperX, upperY);
        this.lowerPt = new Pt(lowerX, lowerY);
    }


    public boolean contains(RectanglePrecise tile) {
        Pt tileUpperPt = new Pt(tile.upperX, tile.upperY);
        Pt tileLowerPt = new Pt(tile.lowerX, tile.lowerY);

        return tileUpperPt.isInside(this) && tileLowerPt.isInside(this);

    }

    public boolean contains(Pt p) {
        return p.isInside(this);
    }


    public boolean intersects(RectanglePrecise tile) {
        Pt tileUpperPt = new Pt(tile.upperX, tile.upperY);
        Pt tileLowerPt = new Pt(tile.lowerX, tile.lowerY);
        Pt tileUpperRightPt = new Pt(tile.lowerX, tile.upperY);
        Pt tileLowerLeftPt = new Pt(tile.upperX, tile.lowerY);

        if (!tileUpperPt.isInside(this) && !tileLowerPt.isInside(this)) {
            if (tileUpperRightPt.isInside(this) && !tileLowerLeftPt.isInside(this)
                    || !tileUpperRightPt.isInside(this) && tileLowerLeftPt.isInside(this)) {
                return true;
            }
        }

        return ((tileUpperPt.isInside(this) && !tileLowerPt.isInside(this))
                || (tileLowerPt.isInside(this) && !tileUpperPt.isInside(this)));
    }

    public String toString() {
        return getClass().getName() + "[(upperX = " + upperX + ", upperY = " + upperY + ") & "
                + "(lowerX = " + lowerX + ", lowerY = " + lowerY + ")]";
    }
}
