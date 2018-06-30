import org.junit.Test;
import utils.Utils;

import static org.junit.Assert.assertEquals;

public class InsertPolygonToDbTest {

    @Test
    public void insertPolygons(){
        assertEquals("inserting polygon to DB",
                true,
                Utils.fileToDB(TestingData.polygonFiles.get(0) ));
    }
}
