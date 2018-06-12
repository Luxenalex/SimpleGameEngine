import window.Window;

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
}
