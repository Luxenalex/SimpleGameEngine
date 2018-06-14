package engine.gfx;

import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImageTest {
    private Image image;

    @Before
    public void setup() {

        try {
            image = new Image("/testRectangle.png");
        } catch (IOException e) {
            System.err.println("Failed to create image at setup");
        }
    }

    @After
    public void teardown() {
        image = null;
    }

    @Test
    public void shouldHaveWidth64() {
        assertEquals(64,image.getWidth());
    }

    @Test
    public void shouldHaveHeight32() {
        assertEquals(32, image.getHeight());
    }

    @Test
    public void shouldHave64x32Pixels() {
        assertEquals(32*64, image.getPixels().length);
    }

    @Test (expected = IllegalArgumentException.class)
    public void imageShouldNotExist() throws IllegalArgumentException, IOException {
        new Image("/shouldNotExist.png");
    }
}
