package engine.window;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class WindowTest {

    private Window window;

    @Before
    public void setup(){
        window = new Window(new WindowSettings(200, 100, 1, "test"));
    }

    @Test
    public void canvasShouldNotBeNull (){
        assertNotNull(window.getCanvas());
    }

    @Test
    public void heightShouldBe100(){
        assertEquals(100, window.getHeight());
    }

    @Test
    public void widthShouldBe200(){
        assertEquals(200, window.getWidth());
    }

}
