package engine;

import engine.gfx.Image;
import engine.window.Window;

import java.awt.image.DataBufferInt;

/**
 * Created by lux on 6/12/18.
 */
public class Renderer {

    private int canvasWidth;
    private int canvasHeight;
    private int[] pixels;

    public Renderer(Window window){
        canvasWidth = window.getWidth();
        canvasHeight = window.getHeight();
        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        if((x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight) ||
           value == 0xFFFF00FF) {
            return;
        }

        pixels[x + y * canvasWidth] = value;
    }

    public void drawImage(Image image, int offsetX, int offsetY) {
        int startX = 0;
        int startY = 0;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        if(offsetX < -imageWidth || offsetY < -imageHeight ||
                offsetX >= canvasWidth || offsetY >= canvasHeight) {
            return;
        }

        if(offsetX < 0){
            startX -= offsetX;
        }
        if(offsetY < 0){
            startY -= offsetY;
        }

        if(imageWidth + offsetX >= canvasWidth){
            imageWidth -= imageWidth + offsetX - canvasWidth;
        }
        if (imageHeight + offsetY >= canvasHeight){
            imageHeight -= imageHeight + offsetY - canvasHeight;
        }

        for(int y = startY; y < imageHeight; y++) {
            for(int x = startX; x < imageWidth; x++) {
                setPixel(
                        x + offsetX,
                        y + offsetY,
                        image.getPixels()[x + y * image.getWidth()]
                );
            }
        }
    }
}
