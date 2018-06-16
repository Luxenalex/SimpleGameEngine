package engine;

import engine.gfx.Font;
import engine.gfx.Image;
import engine.gfx.TileSheet;
import engine.window.Window;

import java.awt.image.DataBufferInt;
import java.io.IOException;

public class Renderer {

    private int canvasWidth;
    private int canvasHeight;
    private int[] pixels;
    private Font font;

    public Renderer(Window window){
        canvasWidth = window.getWidth();
        canvasHeight = window.getHeight();
        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();
        try {
            font = new Font(Font.DEFAULT);
        }
        catch(IOException error) {
            System.err.println("Could not load default font: " + error.getMessage());
        }
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

    public void drawText(String text, int offsetX, int offsetY, int color) {

        Image fontImage = font.getFontImage();
        int asciiPositionsToSkipOver = 32;
        int letterOffset = 0;
        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - asciiPositionsToSkipOver;

            for(int y = 0; y < fontImage.getHeight(); y++) {
                for(int x = 0; x < font.getWidths()[unicode]; x++) {
                    if(fontImage.getPixels()[(x + font.getOffsets()[unicode]) +
                                             y * font.getFontImage().getWidth()]
                       == 0xFFFFFFFF) {
                        setPixel(
                                x + offsetX + letterOffset,
                                y + offsetY,
                                color
                        );
                    }
                }
            }
            letterOffset += font.getWidths()[unicode];
        }
    }

    public void drawImage(Image image, int offsetX, int offsetY,
                          int tileFromLeft, int tileFromTop) {

        if(isOutsideOfCanvas(image, offsetX, offsetY)) return;

        //TODO If this must be expanded to more classes, create Drawable superclass.
        if(!(image instanceof TileSheet)){
            tileFromLeft = 0;
            tileFromTop = 0;
        }

        int startX = 0;
        int startY = 0;

        if(offsetX < 0){
            startX = reduceAreaToDraw(startX, offsetX);
        }
        if(offsetY < 0){
            startY = reduceAreaToDraw(startY, offsetY);
        }

        int imageWidth = image instanceof TileSheet ?
                         ((TileSheet)image).getTileWidth() :
                         image.getWidth();
        int imageHeight = image instanceof TileSheet ?
                          ((TileSheet)image).getTileHeight() :
                          image.getHeight();

        if(imageWidth + offsetX >= canvasWidth){
            imageWidth = reduceAreaToDraw(imageWidth, imageWidth + offsetX - canvasWidth);
        }
        if (imageHeight + offsetY >= canvasHeight){
            imageHeight = reduceAreaToDraw(imageHeight, imageHeight + offsetY - canvasHeight);
        }

        int modifierX = image instanceof TileSheet ?
                        tileFromLeft * ((TileSheet)image).getTileWidth() :
                        tileFromLeft;
        int modifierY = image instanceof TileSheet ?
                        tileFromTop * ((TileSheet)image).getTileHeight() :
                        tileFromTop;

        for(int y = startY; y < imageHeight; y++) {
            for(int x = startX; x < imageWidth; x++) {
                setPixel(
                        x + offsetX,
                        y + offsetY,
                        image.getPixelColor((x + modifierX) + (y + modifierY) * image.getWidth())
                        );
            }
        }
    }

    private int reduceAreaToDraw(int start, int offset) {
        return start - offset;
    }

    private boolean isOutsideOfCanvas(Image image, int offsetX, int offsetY) {
        return offsetX < -image.getWidth() || offsetY < -image.getHeight() ||
               offsetX >= canvasWidth || offsetY >= canvasHeight;
    }
}
