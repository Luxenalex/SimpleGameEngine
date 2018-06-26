package engine.gfx;

import game.EngineLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    private int width;
    private int height;
    private int[] pixels;

    public Image(String path){

        InputStream imageStream = getImageInputStream(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageStream);
        }
        catch(IOException error) {
            EngineLogger.LOGGER.severe(
                    "Could not create buffer from image " + path
            );
        }

        //TODO Do something better than if-then.
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB(0, 0, width, height, null, 0, width);

            image.flush();
        }
    }

    private InputStream getImageInputStream(String path) {
        InputStream imageStream = Image.class.getResourceAsStream(path);
        if(imageStream == null) {
            EngineLogger.LOGGER.warning("Could not read image " + path + ".");
            imageStream = Image.class.getResourceAsStream("/missingImage.png");
            if(imageStream == null) {
                EngineLogger.LOGGER.severe("Could not read placeholder image.");
                System.exit(-1);
            }
        }
        return imageStream;
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
}
