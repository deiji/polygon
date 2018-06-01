import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import polygon.FullPolygon;
import polygon.PolygonPoint;
import utils.PolygonUtils;
import utils.Utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class PolygonCheck {
    static File[] polygonFiles = new File[]{
        new File(PolygonCheck.class.getClassLoader().getResource("polygons/KYIV.geojson").getFile()),
        new File(PolygonCheck.class.getClassLoader().getResource("polygons/REG3.geojson").getFile()),
        new File(PolygonCheck.class.getClassLoader().getResource("polygons/REG4.geojson").getFile()),
        new File(PolygonCheck.class.getClassLoader().getResource("polygons/WEAK.geojson").getFile())
    };
    static FullPolygon[] polygons = new FullPolygon[]{
//        Utils.generatePolygon(Utils.parseFile(polygonFiles[0])),
//        Utils.generatePolygon(Utils.parseFile(polygonFiles[1])),
//        Utils.generatePolygon(Utils.parseFile(polygonFiles[2])),
//        Utils.generatePolygon(Utils.parseFile(polygonFiles[3]))
    };
    static PolygonPoint[][] points = new PolygonPoint[][]{
        { //first point is in polygon, second - is verice, third not in polygon, KYIV
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
            new PolygonPoint(new BigDecimal("33.029490545156946"), new BigDecimal("46.114100510884356")),
            new PolygonPoint(new BigDecimal("30.451307"), new BigDecimal("50.369022"))
        }
    };
    static boolean[][] results = new boolean[][]{
        { true, true, false },
        { true, true, false },
        { true, true, false },
        { true, true, false }
    };;
    static String[] polygonNames = new String[]{
        "Kyiv",
        "reg3",
        "reg4",
        "weak"
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
        for (int i = 0; i < polygonNames.length; i++){
            for(int j = 0; j < points[i].length; j++){
                pairs.add(new Object[]{
                    polygons[i],
                    points[i][j],
                    results[i][j],
                    polygonNames[i]
                });
            }
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
