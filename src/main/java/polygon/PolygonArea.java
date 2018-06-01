package polygon;

import java.util.List;

public class PolygonArea {
    String type;
    List<PolygonPoint> point;

    public PolygonArea(String type, List<PolygonPoint> point) {
        setType(type);
        setPoint(point);
    }

    public PolygonArea() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PolygonPoint> getPoint() {
        return point;
    }

    public void setPoint(List<PolygonPoint> point) {
        this.point = point;
    }
}
