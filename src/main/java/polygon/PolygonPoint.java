package polygon;

import java.math.BigDecimal;

public class PolygonPoint {
    private BigDecimal x;
    private BigDecimal y;
    private int position;

    public PolygonPoint(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }
    public PolygonPoint(BigDecimal x, BigDecimal y, int position) {
        this.x = x;
        this.y = y;
        this.position = position;
    }

    public BigDecimal getX() {
        return x;
    }

    public int getPosition() {
        return position;
    }

    public BigDecimal getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolygonPoint point = (PolygonPoint) o;

        if (!getX().equals(point.getX()))
            return false;
        return getY().equals(point.getY());
    }

    @Override
    public int hashCode() {
        int result = getX().hashCode();
        result = 31 * result + getY().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "polygon.PolygonPoint{" +
                "x=" + x +
                ", y=" + y +
                ", position=" + position +
                '}';
    }
}