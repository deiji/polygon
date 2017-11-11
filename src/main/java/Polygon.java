import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger
;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Polygon {

    final static Logger logger = getLogger(Polygon.class);
    /**
     * Method that reads polygon coordinates
     * from GeoJson file and stores them in
     * the list of PolygonPoint objects
     * @return list of PolygonPoint object
     */
    public static List<PolygonPoint> generatePolygon(){
        List<PolygonPoint> points = new ArrayList<>();
        String URL = "/mnt/laptopHDD/workspace/IdeaProjects/polygon/src/main/java/KYIV.geojson";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
            JsonNode kyivNode = objectMapper.readTree(new FileReader(URL));
            JsonNode coordinates = kyivNode.findValue("coordinates");
            for(int i = 0; i < coordinates.size(); i++){
                JsonNode arrayOfPoints = coordinates.get(i);
                int j = 0;
                for(JsonNode point  : arrayOfPoints){
                    points.add(new PolygonPoint(new BigDecimal(point.get(0).asText()), new BigDecimal(point.get(1).asText()), j));
                    j++;
                }
            }
            logger.debug("Size of points list: " + points.size());

        } catch(IOException ioe){
            logger.error(ioe.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        //need to be done because last point the same as first
        points.remove(points.size() - 1);
        logger.debug("Size of points list after removing last element: " + points.size());
        return points;
    }
}
