import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

public class PebbleTest {
    private Pebble p;

    @Before
    public void setUp(){
        p = new Pebble(5,1);
    }

    @After
    public void tearDown(){
        p = null;
    }

    @Test
    public void testGetBagIndex(){
        assertEquals(1, p.getBagIndex());
    }

    @Test
    public void testGetWeight(){
        assertEquals(5, p.getWeight());
    }

    @Test
    public void testPebbleCreate(){
        p = new Pebble(5, 1);

        assertEquals(5, p.getWeight());
        assertEquals(1, p.getBagIndex());
    }
}
