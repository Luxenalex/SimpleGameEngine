package engine;

import engine.gfx.Font;
import engine.gfx.Image;
import engine.gfx.TileSheet;
import engine.window.Window;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int canvasWidth;
    private int canvasHeight;
    private int[] pixels;
    private Font font;


    public Renderer(Window window){
        canvasWidth = window.getWidth();
        canvasHeight = window.getHeight();
        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();

        font = new Font(Font.DEFAULT);
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        if((x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight) ||
                isAlpha(value)) {
            return;
        }
        pixels[x + y * canvasWidth] = value;
    }

    private boolean isAlpha(int value){
        return ((value >> 24) & 0xff) == 0;
    }

    public void drawText(String text, int offsetX, int offsetY, int color) {

        Image fontImage = font.getFontImage();
        int asciiPositionsToSkipOver = 32;
        int letterOffset = 0;
        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {
            int character = text.codePointAt(i) - asciiPositionsToSkipOver;

            for(int y = 1; y < fontImage.getHeight(); y++) {
                for(int x = 0; x < font.getCharacterWidth(character); x++) {
                    if(!isAlpha(fontImage.getColor(x + font.getCharacterOffset(character), y))) {
                        setPixel(
                            x + offsetX + letterOffset,
                            y - 1 + offsetY,
                            color
                        );
                    }
                }
            }
            letterOffset += font.getCharacterWidth(character);
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
                        image.getColor(x + modifierX, y + modifierY)
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

    private boolean isOutsideOfCanvas(int width, int height, int offsetX, int offsetY) {
        return offsetX < -width || offsetY < -height ||
                offsetX >= canvasWidth || offsetY >= canvasHeight;
    }

    public int getFontHeight() {
        return font.getHeight();
    }

    public void drawRectangle(int offsetX, int offsetY, int width, int height, int color){
        for(int y = 0; y <= height; y++){
            setPixel(offsetX, y + offsetY, color);
            setPixel(offsetX + width, y + offsetY, color);
        }

        for(int x = 0; x <= width; x++){
            setPixel(x + offsetX, offsetY, color);
            setPixel(x + offsetX, offsetY + height, color);
        }
    }

    public void drawFilledRectangle(int offsetX, int offsetY, int width, int height, int color){

        if(isOutsideOfCanvas(width, height, offsetX, offsetY)){
            return;
        }

        int startX = 0;
        int startY = 0;

        if(offsetX < 0){
            startX = reduceAreaToDraw(startX, offsetX);
        }
        if(offsetY < 0){
            startY = reduceAreaToDraw(startY, offsetY);
        }

        if(width + offsetX >= canvasWidth){
            width = reduceAreaToDraw(width, width + offsetX - canvasWidth);
        }
        if (height + offsetY >= canvasHeight){
            height = reduceAreaToDraw(height, height + offsetY - canvasHeight);
        }

        for(int y = startY; y <= height; y++){
            for (int x = startX; x <= width; x++){
                setPixel(x + offsetX, y + offsetY, color);
            }
        }
    }

}
