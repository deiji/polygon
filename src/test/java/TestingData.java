import polygon.FullPolygon;
import utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class TestingData {
    static ArrayList<File> polygonFiles = new ArrayList<>();

    static {
        polygonFiles.add(new File(PolygonCheck.class.getClassLoader().getResource("polygons/KYIV.geojson").getFile()));
        polygonFiles.add(new File(PolygonCheck.class.getClassLoader().getResource("polygons/REG3.geojson").getFile()));
        polygonFiles.add(new File(PolygonCheck.class.getClassLoader().getResource("polygons/REG4.geojson").getFile()));
        polygonFiles.add(new File(PolygonCheck.class.getClassLoader().getResource("polygons/WEAK.geojson").getFile()));
        polygonFiles.add(new File(PolygonCheck.class.getClassLoader().getResource("polygons/TEST.geojson").getFile()));
    }

    static Map<String, FullPolygon> polygons = Utils.generatePolygon(Utils.parseFile(polygonFiles));

    static String[] polygonNames = new String[]{
            "KYIV",
            "REG3",
            "REG4",
            "WEAK",
            "TEST"
    };
}
