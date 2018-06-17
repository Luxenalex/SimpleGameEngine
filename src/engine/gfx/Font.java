package engine.gfx;

import java.io.IOException;

public class Font {

    public static final String DEFAULT = "/fonts/font.png";

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path, int numCharacters)
            throws IOException, IllegalArgumentException{

        if(path == null) {
            path = DEFAULT;
        }
        fontImage = new Image(path);

        offsets = new int[numCharacters];
        widths = new int[numCharacters];

        int index = 0;

        for(int i = 0; i < fontImage.getWidth(); i++){
            if(fontImage.getColor(i, 0) == 0xFF00FF00){
                offsets[index] = i;
            }

            if(fontImage.getColor(i, 0) == 0xFF0000FF){
                widths[index] = i + 1 - offsets[index];
                index++;
            }
        }
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int getHeight() {
        return fontImage.getHeight() - 1;
    }

    public int getCharacterWidth(int character) {
        return widths[character];
    }

    public int getCharacterOffset(int character) {
        return offsets[character];
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }
}
