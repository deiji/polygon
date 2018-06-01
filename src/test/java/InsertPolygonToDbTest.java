import org.junit.Test;
import utils.Utils;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class InsertPolygonToDbTest {
    static File[] polygonFiles = new File[]{
            new File(PolygonCheck.class.getClassLoader().getResource("polygons/KYIV.geojson").getFile())
    };
    @Test
    public void insertPolygons(){
        assertEquals("inserting polygon to DB",
                true,
                Utils.fileToDB(polygonFiles[0]));
    }
}
