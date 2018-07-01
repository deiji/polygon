import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import polygon.FullPolygon;
import polygon.PolygonPoint;
import utils.PolygonUtils;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestCustomPolygon {

    static PolygonPoint[] points = new PolygonPoint[]{};


    private FullPolygon polygon;
    private PolygonPoint point;
    private boolean expected;
    private String polygonName;

    public TestCustomPolygon(FullPolygon polygon, PolygonPoint point, boolean expected, String polygonName){
        this.polygon = polygon;
        this.point = point;
        this.expected = expected;
        this.polygonName = polygonName;
    }

    @Parameters
    public static Collection getPairs()
    {
        Collection pairs = new ArrayList();
        ArrayList<PolygonPoint> points = new ArrayList<>();
        try {
           int[] count = {2
           };
            Path path = Paths.get(TestCustomPolygon.class.getClassLoader().getResource("polygons/data.txt").toURI());
            Stream<String> lines = Files.lines(path);
            lines.forEach(line -> {
                line = line.replace(',', '.');
                String[] data = line.split(";");
                points.add(new PolygonPoint(new BigDecimal(data[0]), new BigDecimal(data[1]), count[0]));
                count[0]++;
            });
            for (int j = 0; j < points.size(); j++) {
                pairs.add(new Object[]{
                        TestingData.polygons.get("TEST"),
                        points.get(j),
                        true,
                        TestingData.polygons.get("TEST").getName()
                });
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return pairs;
    }

    @Test
    public void checkPolygon(){
        assertEquals("Dot to check: " + point.toString(),
                expected,
                PolygonUtils.parsePolygonAndCheck(polygon, point));
    }

}
