package engine.gfx;

import java.io.IOException;

public class Font {

    public static final String DEFAULT = "/fonts/font.png";

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path) throws IOException, IllegalArgumentException{

        if(path == null) {
            path = DEFAULT;
        }
        fontImage = new Image(path);

        offsets = new int[59];
        widths = new int[59];

        int unicode = 0;

        for(int i = 0; i < fontImage.getWidth(); i++){
            if(fontImage.getPixelColor(i) == 0xFF00FF00){
                offsets[unicode] = i;
            }

            if(fontImage.getPixelColor(i) == 0xFF0000FF){
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public Image getFontImage() {
        return fontImage;
    }

    public void setFontImage(Image fontImage) {
        this.fontImage = fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public void setOffsets(int[] offsets) {
        this.offsets = offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }
}
