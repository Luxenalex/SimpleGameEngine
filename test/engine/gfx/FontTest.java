package engine.gfx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class FontTest {

    private Font font;

    @Before
    public void setup() throws IOException, IllegalArgumentException {
        font = new Font("/testFont.png", 5);
    }

    @After
    public void teardown() {
        font = null;
    }

    private int[] getOriginalOffsets() {
        return new int[] {0, 3, 7, 12, 18};
    }

    private int[] getOriginalWidths() {
        return new int[] {3, 4, 5, 6, 2};
    }

    @Test
    public void shouldGetCorrectFontOffset() {
        int[] offsets = getOriginalOffsets();
        assertArrayEquals(offsets, font.getOffsets());
    }

    @Test
    public void shouldGetCorrectWidths() {
        int[] widths = getOriginalWidths();
        assertArrayEquals(widths, font.getWidths());
    }

    @Test
    public void shouldGetFontHeight5() {
        assertEquals(5, font.getHeight());
    }
}