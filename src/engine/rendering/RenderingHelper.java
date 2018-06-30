package engine.rendering;

/**
 * Superclass for the different Renderer helping classes.
 */
public class RenderingHelper {

    int canvasWidth;
    int canvasHeight;
    int pixels[];
    int[] lightBlock;

    RenderingHelper(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.pixels = pixels;
        this.lightBlock = lightBlock;
    }

    boolean isOutsideOfCanvas(int width, int height, int offsetX, int offsetY) {
        return offsetX < -width || offsetY < -height ||
                offsetX >= canvasWidth || offsetY >= canvasHeight;
    }

    boolean isOutsideOfCanvas(int x, int y) {
        return x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight;
    }

    void setPixel(int x, int y, int value, int lightBlock) {

        float alpha = ((value >> 24) & 0xff)/255f;
        if((x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight) ||
                alpha == 0) {
            return;
        }
        if(alpha == 1) {
            pixels[x + y * canvasWidth] = value;
            setLightBlock(x, y, lightBlock);
        }
        else {
            int color = pixels[x + y * canvasWidth];

            int newRed = (value >> 16) & 0xFF;
            int newGreen = (value >> 8) & 0xFF;
            int newBlue = value & 0xFF;
            int oldRed = (color >> 16) & 0xFF;
            int oldGreen = (color >> 8) & 0xFF;
            int oldBlue = color & 0xFF;

            int blendedRed = (int)(newRed * alpha + oldRed * (1 - alpha));
            int blendedGreen = (int)(newGreen * alpha + oldGreen * (1 - alpha));
            int blendedBlue = (int)(newBlue * alpha + oldBlue * (1 - alpha));

            this.pixels[x + y * canvasWidth] = (blendedRed << 16 | blendedGreen << 8 | blendedBlue);
        }
    }

    private void setLightBlock(int x, int y, int value){
        if(isOutsideOfCanvas(x, y)){
            return;
        }
        lightBlock[x + y * canvasWidth] = value;
    }

}
