package engine.rendering;

import engine.gfx.*;
import engine.window.Window;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int[] pixels;
    private int[] lightBlock;
    private ImageHelper imageHelper;
    private LightingHelper lightingHelper;
    private TextHelper textHelper;
    private ShapeHelper shapeHelper;

    public Renderer(Window window){

        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();

        int canvasHeight = window.getHeight();
        int canvasWidth = window.getWidth();
        lightBlock = new int[pixels.length];

        imageHelper = new ImageHelper(canvasWidth, canvasHeight, pixels, lightBlock);
        lightingHelper = new LightingHelper(canvasWidth, canvasHeight, pixels, lightBlock);
        textHelper = new TextHelper(canvasWidth, canvasHeight, pixels, lightBlock);
        shapeHelper = new ShapeHelper(canvasWidth, canvasHeight, pixels, lightBlock);
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
        imageHelper.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void addImageToDraw(Image image, int offsetX, int offsetY, int renderLayer) {
        imageHelper.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void drawImages() {
        imageHelper.drawImages();
        this.pixels = imageHelper.pixels;
    }

    public void drawText(String text, int offsetX, int offsetY, int color) {
        textHelper.drawText(text, offsetX, offsetY, color);
    }

    public int getFontHeight(){
        return textHelper.getFontHeight();
    }

    public void drawTile(TileSheet sheet, int offsetX, int offsetY,
                         int tileFromLeft, int tileFromTop) {
        imageHelper.drawTile(sheet, offsetX, offsetY, tileFromLeft, tileFromTop);
    }

    public void addLightToDraw(Light light, int offsetX, int offsetY) {
        lightingHelper.addLight(new LightRequest(light, offsetX, offsetY));
    }

    public void drawLight() {
        lightingHelper.drawLight();
    }

    public void drawRectangle(int offsetX, int offsetY, int width, int height, int color){
        shapeHelper.drawRectangle(offsetX, offsetY, width, height, color);
    }

    public void drawFilledRectangle(int offsetX, int offsetY, int width, int height, int color){
        shapeHelper.drawFilledRectangle(offsetX, offsetY, width, height, color);
    }

}
