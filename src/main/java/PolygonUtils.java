import java.math.BigDecimal;
import java.util.List;

public class PolygonUtils {
    /**
     * Method that checks all conditions
     * @param points
     * @param testPoint
     * @return
     */
    public static boolean check(List<PolygonPoint> points, PolygonPoint testPoint){
        int vertices = points.size();
        if(checkIfDotInsidePolygon(vertices, points, testPoint) ||
                checkIfDotOnBorder(vertices, points, testPoint) ||
                checkIfDotIsVertex(points, testPoint))
            return true;
        return false;
    }

    /**
     * Method that checks if point is vertex
     * @param points
     * @param testPoint
     * @return boolean result of check
     */
    public static boolean checkIfDotIsVertex(List<PolygonPoint> points, PolygonPoint testPoint){
        return points.contains(testPoint);
    }

    /**
     * Method that checks if dot is in the polygon area
     * based on the algorithm: https://stackoverflow.com/a/2922778
     * @param vertices
     * @param points
     * @param testPoint
     * @return boolean result of check
     */
    public static boolean checkIfDotInsidePolygon(int vertices, List<PolygonPoint> points, PolygonPoint testPoint){
        boolean result = false;
        BigDecimal testX = testPoint.getX();
        BigDecimal testY = testPoint.getY();
        for(int i = 0, j = vertices - 1; i < vertices; j = i++){

            BigDecimal XI = points.get(i).getX();
            BigDecimal YI = points.get(i).getY();
            BigDecimal XJ = points.get(j).getX();
            BigDecimal YJ = points.get(j).getY();

            boolean a = YI.compareTo(testY) > 0;
            boolean b = YJ.compareTo(testY) > 0;
            BigDecimal c = XJ.subtract(XI);
            BigDecimal d = testY.subtract(YI);
            BigDecimal e = c.multiply(d);
            BigDecimal f = YJ.subtract(YI);
            BigDecimal g = e.divide(f, 15, BigDecimal.ROUND_HALF_UP);
            BigDecimal h = g.add(XI);
            boolean k = testX.compareTo(h) < 0;

            if((a != b) && k){
                result = !result;
            }
        }
        return result;
    }

    /**
     * Method that checks if point is on the border of polygon
     * @param vertices
     * @param points
     * @param C
     * @return boolean result of check
     */
    public static boolean checkIfDotOnBorder(int vertices, List<PolygonPoint> points, PolygonPoint C){
        boolean result = false;
        for (int i = 0; i < vertices; i++){
            PolygonPoint A = points.get(i);
            PolygonPoint B = null;
            if(i + 1 == vertices){
                B = points.get(0);
            } else {
                B = points.get(i + 1);
            }

            if(checkIfPointBetweenTwoVertices(A, B, C))
                return true;
        }
        return result;
    }

    /**
     * Method that finds m and b for linear equation:
     * y = mx + b
     * @param first
     * @param second
     * @param test
     * @return boolean result if point is on the line
     * between A and B dots
     */
    public static boolean checkIfPointBetweenTwoVertices(PolygonPoint first, PolygonPoint second, PolygonPoint test){
        BigDecimal i = first.getX().subtract(second.getX());
        BigDecimal j = first.getY().subtract(second.getY());
        BigDecimal m = i.divide(j, 15, BigDecimal.ROUND_HALF_UP);
        BigDecimal mx = m.multiply(first.getX());
        BigDecimal b = first.getY().subtract(mx);

        return checkEquationForDot(m, b, test);
    }

    /**
     * Method that checks if point fits in equation
     * @param m
     * @param b
     * @param test
     * @return boolean result of check
     */
    public static boolean checkEquationForDot(BigDecimal m, BigDecimal b, PolygonPoint test){
        BigDecimal mx = m.multiply(test.getX());
        BigDecimal a = mx.add(b);
        return test.getY().compareTo(a) == 0;
    }
}
