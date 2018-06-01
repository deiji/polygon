import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import polygon.FullPolygon;
import polygon.PolygonPoint;
import utils.PolygonUtils;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;


public class TestPolygonParse {
    private static final List<PolygonPoint> points = new ArrayList<>();
    static List<File> polygonFiles = new ArrayList<>();
    static Map<String, FullPolygon> polygons = new HashMap<>();

    @Before
    public void initialize(){
        polygonFiles.add(new File(getClass().getClassLoader().getResource("polygons/KYIV.geojson").getFile()));
        polygonFiles.add(new File(getClass().getClassLoader().getResource("polygons/REG3.geojson").getFile()));
        polygonFiles.add(new File(getClass().getClassLoader().getResource("polygons/REG4.geojson").getFile()));
        polygonFiles.add(new File(getClass().getClassLoader().getResource("polygons/WEAK.geojson").getFile()));

        ArrayList<JsonNode> jsonNodes = new ArrayList<>();


        try {
            for(File polygonFile : polygonFiles){
                String polygon = new String(Files.readAllBytes(polygonFile.toPath()));
                polygons.put("Kyiv", Utils.generatePolygon());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        polygons.put("reg3", Utils.generatePolygon(Utils.parseFile(polygonFiles.get(1))));
        polygons.put("reg4", Utils.generatePolygon(Utils.parseFile(polygonFiles.get(2))));
        polygons.put("weak", Utils.generatePolygon(Utils.parseFile(polygonFiles.get(3))));

    }
    @Test
    public void checkPointInPolygon(){
        PolygonPoint point = new PolygonPoint(new BigDecimal(3), new BigDecimal(3), 0);
        assertEquals(PolygonUtils.check(points, point), true);
    }
    @Test
    public void checkPointOnPolygon(){
        PolygonPoint point = new PolygonPoint(new BigDecimal(4), new BigDecimal(2));
        assertEquals(PolygonUtils.check(points, point), true);
    }
    @Test
    public void checkPointOnRealPolygon(){
        PolygonPoint point = new PolygonPoint(new BigDecimal("30.451307"), new BigDecimal("50.369022"));
        assertEquals(true, PolygonUtils.parsePolygonAndCheck(polygons.get("Kyiv"), point));
    }
    @Test
    public void checkWhenPointIsVertice(){
        PolygonPoint point = new PolygonPoint(new BigDecimal("31.296580240072014"), new BigDecimal("50.050031664282578"));
        assertEquals(true, PolygonUtils.parsePolygonAndCheck(polygons.get("Kyiv"), point));
    }
    @Test
    public void checkIfPointNotInPolygon(){

        PolygonPoint point = new PolygonPoint(new BigDecimal("30.014552"), new BigDecimal("50.429762"));
        assertEquals(false, PolygonUtils.parsePolygonAndCheck(polygons.get("Kyiv"), point));
    }
    {
        points.add(new PolygonPoint(new BigDecimal(1), new BigDecimal(3), 0));
        points.add(new PolygonPoint(new BigDecimal(3), new BigDecimal(1), 1));
        points.add(new PolygonPoint(new BigDecimal(5), new BigDecimal(3), 2));
        points.add(new PolygonPoint(new BigDecimal(3), new BigDecimal(5), 3));
    }
}
