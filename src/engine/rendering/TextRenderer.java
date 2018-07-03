package engine.rendering;

import engine.gfx.Font;
import engine.gfx.Image;

/**
 * Manages rendering of text.
 */
class TextRenderer extends CanvasRenderer {

    private Font font;

    TextRenderer(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){
        super(canvasWidth, canvasHeight, pixels, lightBlock);

        font = new Font(Font.DEFAULT);
    }

    void drawText(String text, int offsetX, int offsetY, int color) {

        Image fontImage = font.getFontImage();
        int letterOffset = 0;
        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {
            int character = text.codePointAt(i);

            for(int y = 1; y < fontImage.getHeight(); y++) {
                for(int x = 0; x < font.getCharacterWidth(character); x++) {
                    // Sets pixel if color does not have full alpha
                    if((((fontImage.getColor(x + font.getCharacterOffset(character), y)) >> 24) & 0xff) != 0) {
                        super.setPixel(
                                x + offsetX + letterOffset,
                                y - 1 + offsetY,
                                color,
                                fontImage.getLightBlock()
                        );

                    }
                }
            }
            letterOffset += font.getCharacterWidth(character);
        }
    }

    int getFontHeight() {
        return font.getHeight();
    }

}
