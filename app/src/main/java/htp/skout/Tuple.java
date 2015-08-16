package htp.skout;

/**
 * Created by TheFreshDuke on 8/16/15.
 */
public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getZerothElement() {
        return this.x;
    }

    public Y getFirstElement() {
        return this.y;
    }
}
