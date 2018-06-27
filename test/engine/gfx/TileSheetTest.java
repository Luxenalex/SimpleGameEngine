package engine.gfx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileSheetTest {
    private TileSheet image;

    @Before
    public void setup() {
        image = new TileSheet("/testColors.png", 2, 1);
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
    public void shouldGetColorWhite() {

        Image tile = image.getTile(0, 0);
        assertEquals(0xFFFFFFFF, tile.getColor(0, 0));
        assertEquals(0xFFFFFFFF, tile.getColor(1, 0));
    }

    @Test
    public void shouldGetColorBlue() {
        Image tile = image.getTile(0, 1);
        assertEquals(0xFF0000FF, tile.getColor(0, 0));
        assertEquals(0xFF0000FF, tile.getColor(1, 0));
    }

    @Test
    public void shouldGetColorMagenta() {
        Image tile = image.getTile(0, 5);
        assertEquals(0xFFFF00FF, tile.getColor(0, 0));
        assertEquals(0xFFFF00FF, tile.getColor(1, 0));
    }

    @Test
    public void shouldGetColorGrey() {
        Image tile = image.getTile(0, 15);
        assertEquals(0xFF808080, tile.getColor(0, 0));
        assertEquals(0xFF808080, tile.getColor(1, 0));
    }
}
