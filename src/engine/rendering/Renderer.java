package engine.rendering;

import engine.gfx.*;
import engine.window.Window;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int[] pixels;
    private int[] lightBlock;
    private ImageHelper drawableHelper;
    private LightingHelper lightingHelper;
    private TextHelper textHelper;

    public Renderer(Window window){

        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();

        int canvasHeight = window.getHeight();
        int canvasWidth = window.getWidth();
        lightBlock = new int[pixels.length];

        drawableHelper = new ImageHelper(canvasWidth, canvasHeight, pixels, lightBlock);
        lightingHelper = new LightingHelper(canvasWidth, canvasHeight, pixels, lightBlock);
        textHelper = new TextHelper(canvasWidth, canvasHeight, pixels, lightBlock);
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
            lightBlock[i] = 0;
        }
        lightingHelper.clearLightMap();
    }

    public void addImageToDraw(TileSheet sheet, int offsetX, int offsetY,
                               int tileFromLeft, int tileFromTop, int renderLayer) {

        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        image.setLightBlock(sheet.getLightBlock());
        drawableHelper.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void addImageToDraw(Image image, int offsetX, int offsetY, int renderLayer) {
        drawableHelper.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void drawImages() {
        drawableHelper.drawImages();
        this.pixels = drawableHelper.pixels;
    }


    public void drawText(String text, int offsetX, int offsetY, int color) {
        textHelper.drawText(text, offsetX, offsetY, color);
    }

    public int getFontHeight(){
        return textHelper.getFontHeight();
    }



    /* TODO Check if this should be removed or not
    public void drawTile(TileSheet sheet, int offsetX, int offsetY,
                         int tileFromLeft, int tileFromTop) {
        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        drawImage(image, offsetX, offsetY);
    }
    */

    public void addLightToDraw(Light light, int offsetX, int offsetY) {
        lightingHelper.addLight(new LightRequest(light, offsetX, offsetY));
    }

    public void drawLight() {
        lightingHelper.drawLight();
    }

    /* TODO fix
    public void drawRectangle(int offsetX, int offsetY, int width, int height, int color){
        for(int y = 0; y <= height; y++){
            setPixel(offsetX, y + offsetY, color, Light.NONE);
            setPixel(offsetX + width, y + offsetY, color, Light.NONE);
        }

        for(int x = 0; x <= width; x++){
            setPixel(x + offsetX, offsetY, color, Light.NONE);
            setPixel(x + offsetX, offsetY + height, color, Light.NONE);
        }
    }
    */

    /* TODO fix
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
                setPixel(x + offsetX, y + offsetY, color, Light.NONE);
            }
        }
    }
    */



}
