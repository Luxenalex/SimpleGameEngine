package engine.rendering;

import engine.gfx.*;
import engine.window.Window;

import java.awt.image.DataBufferInt;

public class Renderer {

    private ImageRenderer imageRenderer;
    private LightRenderer lightRenderer;
    private TextRenderer textRenderer;
    private ShapeRenderer shapeRenderer;

    private int[] pixels;
    private int[] lightBlock;
    private int[] background = null;
    private int[] backgroundLightBlock = null;

    private int canvasWidth;

    public Renderer(Window window){

        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();

        int canvasHeight = window.getHeight();
        canvasWidth = window.getWidth();
        lightBlock = new int[pixels.length];

        imageRenderer = new ImageRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
        lightRenderer = new LightRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
        textRenderer = new TextRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
        shapeRenderer = new ShapeRenderer(canvasWidth, canvasHeight, pixels, lightBlock);
    }

    public void clear(){
        if(background != null) {
            clearToBackground();
        }
        else {
            clearToBlack();
        }
        lightRenderer.clearLightMap();
    }

    private void clearToBackground() {
        System.arraycopy(background, 0, pixels, 0,
                         background.length);
        System.arraycopy(backgroundLightBlock, 0, lightBlock, 0,
                         backgroundLightBlock.length);
    }

    private void clearToBlack() {
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
            lightBlock[i] = 0;
        }
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

    public void setBackground(TileSheet tileSheet, int[][] tilePlacements) {

        if(background != null) {
            return;
        }
        int offsetX = -tileSheet.getTileWidth();
        int offsetY = 0;
        for(int[] tilePlacement : tilePlacements) {
            offsetX = (offsetX + tileSheet.getTileWidth()) % canvasWidth;

            addImageToDraw(tileSheet, offsetX, offsetY, tilePlacement[0],
                           tilePlacement[1], 0);

            if(offsetX == (canvasWidth - tileSheet.getTileWidth())) {
                offsetY += tileSheet.getTileHeight();
            }
        }
        drawImages();
        background = new int[pixels.length];
        backgroundLightBlock = new int[lightBlock.length];
        System.arraycopy(pixels, 0, background, 0, pixels.length);
    }
}
