package engine.gfx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class ImageTest {
    private Image image;

    @Before
    public void setup() throws IOException, IllegalArgumentException {
        image = new Image("/testColors.png");
    }

    private int[] getOriginalColors() {
        return new int[] {
                0xFFFFFFFF,
                0xFFFFFFFF, //First FF is for alpha.
                0xFF0000FF,
                0xFF0000FF,
                0xFFFFFFFF,
                0xFFFFFFFF,
                0xFF00FFFF,
                0xFF00FFFF,
                0xFFFFFFFF,
                0xFFFFFFFF,
                0xFFFF00FF,
                0xFFFF00FF,
                0xFFFFFFFF,
                0xFFFFFFFF,
                0xFF000000,
                0xFF000000,
                0xFFFFFFFF,
                0xFFFFFFFF,
                0xFF00FF00,
                0xFF00FF00,
                0xFFFFFFFF,
                0xFFFFFFFF,
                0xFFFFFF00,
                0xFFFFFF00,
                0xFFFFFFFF,
                0xFFFFFFFF,
                0xFFFF0000,
                0xFFFF0000,
                0xFFFFFFFF,
                0xFFFFFFFF,
                0xFF808080,
                0xFF808080
        };
    }

    @After
    public void teardown() {
        image = null;
    }

    @Test
    public void shouldHaveWidth2() {
        assertEquals(2,image.getWidth());
    }

    @Test
    public void shouldHaveHeight16() {
        assertEquals(16, image.getHeight());
    }

    @Test
    public void shouldHave2x16Pixels() {
        assertEquals(2*16, image.getPixels().length);
    }

    @Test (expected = IllegalArgumentException.class)
    public void imageShouldNotExist() throws IllegalArgumentException, IOException {
        new Image("/shouldNotExist.png");
    }

    @Test
    public void imageShouldHaveCorrectColors() {
        int[] colors = getOriginalColors();
        assertArrayEquals(colors, image.getPixels());
    }

    @Test
    public void shouldGetColorWhite() {
        assertEquals(0xFFFFFFFF, image.getColor(0, 0));
    }

    @Test
    public void shouldGetColorBlue() {
        assertEquals(0xFF0000FF, image.getColor(1, 1));
    }

    @Test
    public void shouldGetColorMagenta() {
        assertEquals(0xFFFF00FF, image.getColor(0, 5));
    }

    @Test
    public void shouldGetColorGrey() {
        assertEquals(0xFF808080, image.getColor(1, 15));
    }
}
