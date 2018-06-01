import org.junit.Test;
import utils.Utils;

import javax.rmi.CORBA.Util;

import static org.junit.Assert.assertEquals;

public class GetPolygonFromDbTest {

    @Test
    public void insertPolygons(){
        assertEquals("inserting polygon to DB",
                true,
                Utils.generatePolygon(Utils.parseFile(Utils.dbToFile())));
    }
}
