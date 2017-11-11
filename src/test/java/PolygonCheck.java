import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PolygonCheck {
    private static final List<PolygonPoint> points = new ArrayList<>();
    {
        points.add(new PolygonPoint(new BigDecimal(1), new BigDecimal(3), 0));
        points.add(new PolygonPoint(new BigDecimal(3), new BigDecimal(1), 1));
        points.add(new PolygonPoint(new BigDecimal(5), new BigDecimal(3), 2));
        points.add(new PolygonPoint(new BigDecimal(3), new BigDecimal(5), 3));
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
        List<PolygonPoint> points = Polygon.generatePolygon();
        assertEquals(PolygonUtils.check(points, point), true);
    }
    @Test
    public void checkWhenPointIsVertice(){
        PolygonPoint point = new PolygonPoint(new BigDecimal("31.296580240072014"), new BigDecimal("50.050031664282578"));
        List<PolygonPoint> points = Polygon.generatePolygon();
        assertEquals(PolygonUtils.check(points, point), true);
    }
    @Test
    public void checkIfPointNotInPolygon(){

        PolygonPoint point = new PolygonPoint(new BigDecimal("30.014552"), new BigDecimal("50.429762"));
        List<PolygonPoint> points = Polygon.generatePolygon();
        assertEquals(PolygonUtils.check(points, point), false);
    }
}
