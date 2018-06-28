package engine.gfx;

public class Font {

    public static final String DEFAULT = "/fonts/courier.png";
    private static final int CHAR_START = 0xFF0000FF;
    private static final int CHAR_STOP = 0xFFFFFF00;

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path) {

        if(path == null) {
            path = DEFAULT;
        }
        fontImage = new Image(path);

        int numCharacters = getNumCharactersInFont();
        offsets = new int[numCharacters];
        widths = new int[numCharacters];

        int index = 0;
        for(int i = 0; i < fontImage.getWidth(); i++){
            if(fontImage.getColor(i, 0) == CHAR_START){
                offsets[index] = i;
            }
            if(fontImage.getColor(i, 0) == CHAR_STOP){
                widths[index] = i + 1 - offsets[index];
                index++;
            }
        }
    }

    private int getNumCharactersInFont() {
        int numCharacters = 0;

        for(int i = 0; i < fontImage.getWidth(); i++) {
            if(fontImage.getColor(i, 0) == CHAR_START){
                numCharacters++;
            }
        }
        if(numCharacters == 0) {
            numCharacters++;
        }
        return numCharacters;
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
