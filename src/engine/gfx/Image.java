package engine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {
    private int width;
    private int height;
    private int[] pixels;

    public Image(String path) throws IllegalArgumentException, IOException {
        BufferedImage image = ImageIO.read(Image.class.getResourceAsStream(path));

        //TODO Do something better than if-then.
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB(0, 0, width, height, null, 0, width);

            image.flush();
        }
    }

    public int getColor(int x, int y) {
        return pixels[x + y * width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getPixelColor(int index) {
        return pixels[index];
    }
}
