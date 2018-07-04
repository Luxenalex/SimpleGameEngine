package engine.rendering;

import engine.Position;
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

    public void addImageToDraw(TileSheet sheet, Position offset,
                               Position tilePosition, int renderLayer) {

        Image image = sheet.getTile(tilePosition);
        image.setLightBlock(sheet.getLightBlock());
        imageRenderer.addDrawable(new OffsetImage(image, offset, renderLayer));
    }

    public void addImageToDraw(Image image, Position offset, int renderLayer) {
        imageRenderer.addDrawable(new OffsetImage(image, offset, renderLayer));
    }

    public void drawImages() {
        imageRenderer.drawImages();
    }

    public void drawText(String text, Position offset, int color) {
        textRenderer.drawText(text, offset, color);
    }

    public int getFontHeight(){
        return textRenderer.getFontHeight();
    }

    public void drawTile(TileSheet sheet, Position offset, Position tilePosition) {
        imageRenderer.drawTile(sheet, offset, tilePosition);
    }

    public void addLightToDraw(Light light, Position offset) {
        lightRenderer.addLight(new LightRequest(light, offset));
    }

    public void drawLight() {
        lightRenderer.drawLight();
    }

    public void drawRectangle(Position offset, int width, int height, int color){
        shapeRenderer.drawRectangle(offset, width, height, color);
    }

    public void drawFilledRectangle(Position offset, int width, int height, int color){
        shapeRenderer.drawFilledRectangle(offset, width, height, color);
    }

    public void setBackground(TileSheet tileSheet, int[][] tilePlacements) {

        if(background != null) {
            return;
        }
        int offsetX = -tileSheet.getTileWidth();
        int offsetY = 0;
        for(int[] tilePlacement : tilePlacements) {
            offsetX = (offsetX + tileSheet.getTileWidth()) % canvasWidth;

            addImageToDraw(tileSheet, new Position(offsetX, offsetY), new Position(tilePlacement[0], tilePlacement[1]), 0);

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
