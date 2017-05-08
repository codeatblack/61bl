/**
 * Created by cs61bl-gv on 7/14/16.
 */
public class Pt {

    double xx;
    double yy;

    public Pt(double x, double y) {
        this.xx = x;
        this.yy = y;
    }

    public String toString() {
        return "(" + xx + ", " + yy + ")";
    }

    public boolean isToTheLeftOf(Pt input) {
        return xx <= input.xx;
    }

    public boolean isToTheRightOf(Pt input) {
        return xx >= input.xx;
    }

    public boolean isBelow(Pt input) {
        return yy <= input.yy;
    }

    public boolean isAbove(Pt input) {
        return yy >= input.yy;
    }

    public boolean isInside(RectanglePrecise r) {
        return (isBelow(r.upperPt) && isToTheRightOf(r.upperPt)
                && isAbove(r.lowerPt) && isToTheLeftOf(r.lowerPt));
    }

    public boolean iisToTheLeftOf(Pt input) {
        return xx < input.xx;
    }

    public boolean iisToTheRightOf(Pt input) {
        return xx > input.xx;
    }

    public boolean iisBelow(Pt input) {
        return yy < input.yy;
    }

    public boolean iisAbove(Pt input) {
        return yy > input.yy;
    }

    public boolean iisInside(RectanglePrecise r) {
        return (iisBelow(r.upperPt) && iisToTheRightOf(r.upperPt)
                && iisAbove(r.lowerPt) && iisToTheLeftOf(r.lowerPt));
    }
}
