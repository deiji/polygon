package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import polygon.FullPolygon;
import polygon.PolygonArea;
import polygon.PolygonPoint;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    static String insertPoint = "insert into maximo.polygons (name, polygon, insertby, insertdate) " +
            "values (?, ?, ?, sysdate)";
    static String getPolygon = "select name, polygon from maximo.polygons";

    private static final Logger logger = LogManager.getLogger(Utils.class);

    public static ArrayList<JsonNode> parseFile(ArrayList<File> files){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        try {
            ArrayList<JsonNode> jsonNodes = new ArrayList<>();
            for(File file : files) {
                jsonNodes.add(objectMapper.readTree(file));
            }
            return jsonNodes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Method that reads polygon coordinates
     * from GeoJson file and stores them in
     * the list of PolygonPoint objects
     * @return list of PolygonPoint object
     */
    public static Map<String, FullPolygon> generatePolygon(ArrayList<JsonNode> jsonNodes){
        Map<String, FullPolygon> polygons = new HashMap<>();
        for (JsonNode jsonNode : jsonNodes) {
            List<PolygonArea> polygonArea = new ArrayList<>();
            String name = jsonNode.get("name").asText();
            try {
                JsonNode coordinates = jsonNode.findValue("coordinates");
                for (int i = 0; i < coordinates.size(); i++) {
                    JsonNode arrayOfPoints = coordinates.get(i);
                    List<PolygonPoint> points = new ArrayList<>();
                    for (int j = 0; j < arrayOfPoints.size(); j++) {
                        JsonNode array = arrayOfPoints.get(j);

                        BigDecimal x;
                        BigDecimal y;

                        if (array.size() == 2) {
                            x = new BigDecimal(array.get(0).asText());
                            y = new BigDecimal(array.get(1).asText());
                            points.add(new PolygonPoint(x, y, j));
                        } else {
                            points = new ArrayList<>();
                            for (int k = 0; k < array.size(); k++) {
                                x = new BigDecimal(array.get(k).get(0).asText());
                                y = new BigDecimal(array.get(k).get(1).asText());
                                points.add(new PolygonPoint(x, y, k));
                            }
                            if (j == 0) {
                                polygonArea.add(new PolygonArea("polygon" + i, points));
                            } else {
                                polygonArea.add(new PolygonArea("hole" + j, points));
                            }
                        }
                        if (polygonArea.size() == 0) {
                            polygonArea.add(new PolygonArea("polygon", points));
                        }
                    }
                }
                polygons.put(name, new FullPolygon(polygonArea, name));
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return polygons;
    }

    /**
     * Method that is used for inserting file to DB
     * @param file - file with polygon data
     * @return boolean result of inserting file to DB
     */
    public static boolean fileToDB(File file){
        logger.info("start inserting into db");
        boolean executed = false;
        PreparedStatement st = null;
        Connection connection = null;

        try{
            connection = DBUtils.createConnection("242");
            st = connection.prepareStatement(insertPoint);
            Clob clob = connection.createClob();

            String stringFile = new String(Files.readAllBytes(file.toPath()));
            clob.setString(1, stringFile);

            st.setString(1, file.getName());
            st.setClob(2, clob);
            st.setString(3, "MAXIMO");
            st.executeUpdate();

            executed = true;
            logger.debug("executed successfuly insert polygon point sql");
        } catch(SQLException se){
            logger.error("exception while executing sql insert polygon point: " + se.getMessage());
        } catch (Exception e) {
            logger.error("other exception " + e.getMessage());
        } finally {
            try {
                if(executed){
                    connection.commit();
                } else {
                    connection.rollback();
                }
                if(st != null)
                    st.close();
                return true;
            } catch (Exception ee) {
                logger.error("exception while closing statement " + ee.getMessage());
                return false;
            }
        }
    }

    /**
     * Method that is used for getting polygon from DB
     * @return FullPolygon object
     */
    public static ArrayList<String> dbToFile(){
        logger.info("start getting polygon from db");

        try (Connection connection = DBUtils.createConnection("242");
             Statement st = connection.createStatement()){
            st.execute(getPolygon);
            ResultSet resultSet = st.getResultSet();
            ArrayList<String> polygon = new ArrayList<>();
            while (resultSet.next()){
                polygon.add(resultSet.getString("polygon"));
            }
            logger.debug("executed successfuly insert polygon point sql");
            return polygon;
        } catch(SQLException se){
            logger.error("exception while executing sql insert polygon point: " + se.getMessage());
        } catch (Exception e) {
            logger.error("other exception " + e.getMessage());
        }
        return null;
    }
}
