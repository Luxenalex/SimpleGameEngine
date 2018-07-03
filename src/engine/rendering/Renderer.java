package engine.rendering;

import engine.gfx.*;
import engine.window.Window;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int[] pixels;
    private int[] lightBlock;
    private ImageRenderer imageRenderer;
    private LightRenderer lightRenderer;
    private TextRenderer textRenderer;
    private ShapeRenderer shapeRenderer;

    public Renderer(Window window){

        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();

        int canvasHeight = window.getHeight();
        int canvasWidth = window.getWidth();
        lightBlock = new int[pixels.length];

        imageRenderer = new ImageRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
        lightRenderer = new LightRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
        textRenderer = new TextRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
        shapeRenderer = new ShapeRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
            lightBlock[i] = 0;
        }
        lightRenderer.clearLightMap();
    }

    public void addImageToDraw(TileSheet sheet, int offsetX, int offsetY,
                               int tileFromLeft, int tileFromTop, int renderLayer) {

        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        image.setLightBlock(sheet.getLightBlock());
        imageRenderer.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void addImageToDraw(Image image, int offsetX, int offsetY, int renderLayer) {
        imageRenderer.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void drawImages() {
        imageRenderer.drawImages();
        this.pixels = imageRenderer.pixels;
    }

    public void drawText(String text, int offsetX, int offsetY, int color) {
        textRenderer.drawText(text, offsetX, offsetY, color);
    }

    public int getFontHeight(){
        return textRenderer.getFontHeight();
    }

    public void drawTile(TileSheet sheet, int offsetX, int offsetY,
                         int tileFromLeft, int tileFromTop) {
        imageRenderer.drawTile(sheet, offsetX, offsetY, tileFromLeft, tileFromTop);
    }

    public void addLightToDraw(Light light, int offsetX, int offsetY) {
        lightRenderer.addLight(new LightRequest(light, offsetX, offsetY));
    }

    public void drawLight() {
        lightRenderer.drawLight();
    }

    public void drawRectangle(int offsetX, int offsetY, int width, int height, int color){
        shapeRenderer.drawRectangle(offsetX, offsetY, width, height, color);
    }

    public void drawFilledRectangle(int offsetX, int offsetY, int width, int height, int color){
        shapeRenderer.drawFilledRectangle(offsetX, offsetY, width, height, color);
    }

}
