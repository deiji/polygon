import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.DBUtils;
import utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class InsertPolygonToDbTimeTest {
    private static final Logger logger = LogManager.getLogger(InsertPolygonToDbTimeTest.class);

    static boolean[] results = new boolean[]{
            true,
            true,
            true,
            true
    };

    private File polygon;
    private boolean expected;
    private String polygonName;
    private static long startTime;
    private static long endTime;

    public InsertPolygonToDbTimeTest(File polygon, boolean expected, String polygonName){
        this.polygon = polygon;
        this.expected = expected;
        this.polygonName = polygonName;
    }

    @Parameterized.Parameters
    public static Collection getPairs()
    {

        Collection pairs = new ArrayList();
        int i = 0;
        for (String polygon : TestingData.polygonNames){
            pairs.add(new Object[]{
                    TestingData.polygons.get(polygon),
                    results[i],
                    TestingData.polygonNames[i]
            });
            i++;
        }
        return pairs;
    }

    @BeforeClass
    public static void setUp() {
        logger.info("starting testing");
        DBUtils.executeSQL("clear maximo.polygons", "delete from maximo.polygons");
        startTime = System.nanoTime();
    }

    @Test
    public void insertPolygons(){
        logger.info("insert to db started");
        assertEquals("Polygon insertion into DB: " + polygonName,
                expected,
                Utils.fileToDB(polygon));

        logger.info("insert to db finished");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        endTime = System.nanoTime();
        long totalTime = (endTime - startTime) / 1_000_000_000;
        logger.info("total time -> " + totalTime + " seconds");
    }
}
