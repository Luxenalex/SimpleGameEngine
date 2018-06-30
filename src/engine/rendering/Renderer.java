package engine.rendering;

import engine.gfx.*;
import engine.window.Window;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Renderer {

    private int canvasWidth;
    private int canvasHeight;
    protected int[] pixels;
    private int[] lightMap;
    private int[] lightBlock;
    private int ambientLighting = 0xFF6b6b6b;
    private Font font;
    private DrawableHandler drawableHandler;
    private LightingHandler lightingHandler;

    private ArrayList<LightRequest> lights;

    public Renderer(Window window){
        canvasWidth = window.getWidth();
        canvasHeight = window.getHeight();
        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();

        lightMap = new int[pixels.length];
        lightBlock = new int[pixels.length];
        font = new Font(Font.DEFAULT);

        // Does canvas height and width have to be saved in renderer?
        drawableHandler = new DrawableHandler(canvasWidth, canvasHeight, pixels);
        lightingHandler = new LightingHandler(canvasWidth, canvasHeight, pixels.length);

        lights = new ArrayList<>();
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
            //lightMap[i] = ambientLighting;
            //lightBlock[i] = 0;
        }
        lightingHandler.clear();
    }

    public void addImageToDraw(TileSheet sheet, int offsetX, int offsetY,
                               int tileFromLeft, int tileFromTop, int renderLayer) {
        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        image.setLightBlock(sheet.getLightBlock());

        drawableHandler.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void addImageToDraw(Image image, int offsetX, int offsetY, int renderLayer) {
        drawableHandler.addDrawable(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void drawImages() {
        drawableHandler.drawImages();
        this.pixels = drawableHandler.pixels;
    }

    /*
    public void drawText(String text, int offsetX, int offsetY, int color) {

        Image fontImage = font.getFontImage();
        int letterOffset = 0;
        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {
            int character = text.codePointAt(i);

            for(int y = 1; y < fontImage.getHeight(); y++) {
                for(int x = 0; x < font.getCharacterWidth(character); x++) {
                    // Sets pixel if color does not have full alpha
                    if((((fontImage.getColor(x + font.getCharacterOffset(character), y)) >> 24) & 0xff) != 0) {
                        setPixel(
                            x + offsetX + letterOffset,
                            y - 1 + offsetY,
                            color,
                            fontImage.getLightBlock()
                        );

                    }
                }
            }
            letterOffset += font.getCharacterWidth(character);
        }
    }
    */

    /* TODO Check if this should be removed or not
    public void drawTile(TileSheet sheet, int offsetX, int offsetY,
                         int tileFromLeft, int tileFromTop) {
        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        drawImage(image, offsetX, offsetY);
    }
    */

    /*
    public void drawImage(Image image, int offsetX, int offsetY) {

        if(isOutsideOfCanvas(image.getWidth(), image.getHeight(), offsetX, offsetY)) {
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

        int imageWidth = image.getWidth();
        if(imageWidth + offsetX >= canvasWidth){
            imageWidth = reduceAreaToDraw(imageWidth, imageWidth + offsetX - canvasWidth);
        }

        int imageHeight = image.getHeight();
        if (imageHeight + offsetY >= canvasHeight){
            imageHeight = reduceAreaToDraw(imageHeight, imageHeight + offsetY - canvasHeight);
        }

        for(int y = startY; y < imageHeight; y++) {
            for(int x = startX; x < imageWidth; x++) {
                setPixel(
                        x + offsetX,
                        y + offsetY,
                        image.getColor(x, y),
                        image.getLightBlock()
                        );
            }
        }
    }
    */
    /*
    public void setPixel(int x, int y, int value, int lightBlock) {

        float alpha = ((value >> 24) & 0xff)/255f;
        if((x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight) ||
           alpha == 0) {
            return;
        }
        if(alpha == 1) {
            pixels[x + y * canvasWidth] = value;
            setLightBlock(x, y, lightBlock);
        }
        else {
            int color = pixels[x + y * canvasWidth];

            int newRed = (value >> 16) & 0xFF;
            int newGreen = (value >> 8) & 0xFF;
            int newBlue = value & 0xFF;
            int oldRed = (color >> 16) & 0xFF;
            int oldGreen = (color >> 8) & 0xFF;
            int oldBlue = color & 0xFF;

            int blendedRed = (int)(newRed * alpha + oldRed * (1 - alpha));
            int blendedGreen = (int)(newGreen * alpha + oldGreen * (1 - alpha));
            int blendedBlue = (int)(newBlue * alpha + oldBlue * (1 - alpha));

            pixels[x + y * canvasWidth] = (blendedRed << 16 | blendedGreen << 8 | blendedBlue);
        }
    }
    */

    /*
    private int reduceAreaToDraw(int start, int offset) {
        return start - offset;
    }
    */

    /*
    private boolean isOutsideOfCanvas(int width, int height, int offsetX, int offsetY) {
        return offsetX < -width || offsetY < -height ||
                offsetX >= canvasWidth || offsetY >= canvasHeight;
    }
    */

    /*
    private boolean isOutsideOfCanvas(int x, int y) {
        return x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight;
    }
    */

    public int getFontHeight() {
        return font.getHeight();
    }


    /*
    public void setLightMapPixel(int x, int y, int value){
        if(isOutsideOfCanvas(x, y)){
            return;
        }

        int baseColor = lightMap[x + y * canvasWidth];

        int baseRed = (baseColor >> 16) & 0xFF;
        int baseGreen = (baseColor >> 8) & 0xFF;
        int baseBlue = baseColor & 0xFF;
        int newRed = (value >> 16) & 0xFF;
        int newGreen = (value >> 8) & 0xFF;
        int newBlue = value & 0xFF;

        int maxRed = Math.max(baseRed, newRed);
        int maxGreen = Math.max(baseGreen, newGreen);
        int maxBlue = Math.max(baseBlue, newBlue);

        lightMap[x + y * canvasWidth] = (maxRed << 16 | maxGreen << 8 | maxBlue);
    }
    */

    /*
    public void setLightBlock(int x, int y, int value){
        if(isOutsideOfCanvas(x, y)){
            return;
        }

        lightBlock[x + y * canvasWidth] = value;
    }
    */

    public void addLightToDraw(Light light, int offsetX, int offsetY) {
        lightingHandler.addLight(new LightRequest(light, offsetX, offsetY));
        //lights.add(new LightRequest(light, offsetX, offsetY));
    }

    public void drawLight() {

        lightingHandler.setPixels(this.pixels);
        lightingHandler.drawLight();
        this.pixels =lightingHandler.getPixels();

        /*
        for(LightRequest request : lights) {
            setLight(request.getLight(), request.getCenterX(), request.getCenterY());
        }
        lights.clear();

        for(int i = 0; i < pixels.length; i++){
            float lightMapRed = ((lightMap[i] >> 16) & 0xFF) / 255f;
            float lightMapGreen = ((lightMap[i] >> 8) & 0xFF) / 255f;
            float lightMapBlue = ((lightMap[i]) & 0xFF) / 255f;
            int red = (pixels[i] >> 16) & 0xFF;
            int green = (pixels[i] >> 8) & 0xFF;
            int blue = pixels[i] & 0xFF;

            pixels[i] = (int)(red * lightMapRed) << 16 |
                        (int)(green * lightMapGreen) << 8 |
                        (int)(blue * lightMapBlue);
        }
        */
    }

    /*
    public void setLight(Light light, int offsetX, int offsetY){
        int radius = light.getRadius();
        int diameter = light.getDiameter();

        for(int i = 0; i <= light.getDiameter(); i++) {
            setLightLine(light, radius, radius, i, 0, offsetX, offsetY);
            setLightLine(light, radius, radius, i, diameter, offsetX, offsetY);
            setLightLine(light, radius, radius, 0, i, offsetX, offsetY);
            setLightLine(light, radius, radius, diameter, i, offsetX, offsetY);
        }
    }
    */

    /*
    private void setLightLine(Light light, int startX, int startY, int endX, int endY, int offsetX, int offsetY) {
        int dX = Math.abs(endX - startX);
        int dY = Math.abs(endY - startY);

        int sX = startX < endX ? 1 : -1;
        int sY = startY < endY ? 1 : -1;

        int error = dX - dY;
        int error2;

        while(true) {
            int screenX = startX - light.getRadius() + offsetX;
            int screenY = startY - light.getRadius() + offsetY;

            if(isOutsideOfCanvas(screenX, screenY)){
                return;
            }

            int lightColor = light.getLight(startX, startY);
            if(lightColor == 0) {
                return;
            }

            if(lightBlock[screenX + screenY * canvasWidth] == Light.FULL){
                return;
            }

            setLightMapPixel(screenX, screenY, lightColor);
            if(startX == endX && startY == endY) {
                break;
            }

            error2 = 2 * error;
            if(error2 > -1 * dY) {
                error -= dY;
                startX += sX;
            }
            if(error2 < dX) {
                error += dX;
                startY += sY;
            }
        }
    }
    */

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
