package polygon;

import java.util.List;

public class FullPolygon {
    private List<PolygonArea> polygonWithHoles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public FullPolygon(List<PolygonArea> polygonWithHoles, String name) {
        this.polygonWithHoles = polygonWithHoles;
        setName(name);
    }

    public FullPolygon() {
    }

    public List<PolygonArea> getPolygonWithHoles() {
        return polygonWithHoles;
    }

    public void setPolygonWithHoles(List<PolygonArea> polygonWithHoles) {
        this.polygonWithHoles = polygonWithHoles;
    }
}
