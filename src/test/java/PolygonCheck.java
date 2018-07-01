import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import polygon.FullPolygon;
import polygon.PolygonPoint;
import utils.PolygonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class PolygonCheck {

    static PolygonPoint[][] points = new PolygonPoint[][]{
//            first point is in polygon, second - is vertex, third - not in polygon
        { //KYIV
            new PolygonPoint(new BigDecimal("30.451307"), new BigDecimal("50.369022")),
            new PolygonPoint(new BigDecimal("31.296580240072014"), new BigDecimal("50.050031664282578")),
            new PolygonPoint(new BigDecimal("30.014552"), new BigDecimal("50.429762"))
        },
        { //REG3
            new PolygonPoint(new BigDecimal("25.574752"), new BigDecimal("49.550465")),
            new PolygonPoint(new BigDecimal("40.22216078097631"), new BigDecimal("49.251576468502293")),
            new PolygonPoint(new BigDecimal("30.451307"), new BigDecimal("50.369022"))
        },
        { //REG4
            new PolygonPoint(new BigDecimal("25.574752"), new BigDecimal("49.550465")),
            new PolygonPoint(new BigDecimal("40.227084286280508"), new BigDecimal("49.259820749949938")),
            new PolygonPoint(new BigDecimal("26.962641"), new BigDecimal("49.409637"))
        },
        { //WEAK
            new PolygonPoint(new BigDecimal("25.574752"), new BigDecimal("49.550465")),
            new PolygonPoint(new BigDecimal("40.227084286280508"), new BigDecimal("49.259820749949938")),
            new PolygonPoint(new BigDecimal("30.451307"), new BigDecimal("50.369022"))
        }
    };
    static boolean[][] results = new boolean[][]{
        { true, true, false },
        { true, true, false },
        { true, true, false },
        { true, true, false }
    };



    private FullPolygon polygon;
    private PolygonPoint point;
    private boolean expected;
    private String polygonName;

    public PolygonCheck(FullPolygon polygon, PolygonPoint point, boolean expected, String polygonName){
        this.polygon = polygon;
        this.point = point;
        this.expected = expected;
        this.polygonName = polygonName;
    }

    @Parameters
    public static Collection getPairs()
    {

        Collection pairs = new ArrayList();
        int i = 0;
        for (String polygon : TestingData.polygonNames){
            for(int j = 0; j < points[i].length; j++){
                pairs.add(new Object[]{
                    TestingData.polygons.get(polygon),
                    points[i][j],
                    results[i][j],
                    TestingData.polygons.get(polygon).getName()
                });
            }
            i++;
        }
        return pairs;
    }

    @Test
    public void checkPolygons(){
        assertEquals("Polygon to check: " + polygonName,
                expected,
                PolygonUtils.parsePolygonAndCheck(polygon, point));
    }
}
