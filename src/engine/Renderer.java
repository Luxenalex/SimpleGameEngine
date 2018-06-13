package engine;

import engine.gfx.Image;
import engine.window.Window;

import java.awt.image.DataBufferInt;

/**
 * Created by lux on 6/12/18.
 */
public class Renderer {

    private int pixelWidth;
    private int pixelHeight;
    private int[] pixels;

    public Renderer(Window window){
        pixelWidth = window.getWidth();
        pixelHeight = window.getHeight();
        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        if((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) ||
           value == 0xFFFF00FF) {
            return;
        }

        pixels[x + y * pixelWidth] = value;
    }

    public void drawImage(Image image, int offsetX, int offsetY) {
        for(int y = 0; y < image.getHeight(); y++) {
            for(int x = 0; x < image.getWidth(); x++) {
                setPixel(
                        x + offsetX,
                        y + offsetY,
                        image.getPixels()[x + y * image.getWidth()]
                );
            }
        }
    }
}
