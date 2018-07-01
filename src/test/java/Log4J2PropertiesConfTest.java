import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4J2PropertiesConfTest {
    private static final Logger logger = LogManager.getLogger(Log4J2PropertiesConfTest.class);

    @Test
    public void testPerformSomeTask() throws Exception {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");
    }
}