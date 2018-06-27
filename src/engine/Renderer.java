package engine;

import engine.gfx.Font;
import engine.gfx.Image;
import engine.gfx.OffsetImage;
import engine.gfx.TileSheet;
import engine.window.Window;

import java.awt.image.DataBufferInt;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Renderer {

    private int canvasWidth;
    private int canvasHeight;
    private int[] pixels;
    private int[] zBuffer;
    private int zDepth = 0;
    private Font font;

    private PriorityQueue<OffsetImage> drawables;


    public Renderer(Window window){
        canvasWidth = window.getWidth();
        canvasHeight = window.getHeight();
        pixels = ((DataBufferInt)window.getImageRasterDataBuffer()).getData();

        zBuffer = new int[pixels.length];
        font = new Font(Font.DEFAULT);

        drawables = new PriorityQueue<>(50, new Comparator<OffsetImage>() {
            @Override
            public int compare(OffsetImage o1, OffsetImage o2) {

                if(o1.getRenderLayer() < o2.getRenderLayer()) {
                    return -1;
                }
                if(o1.getRenderLayer() > o2.getRenderLayer()) {
                    return 1;
                }

                return 0;
            }
        });
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
            zBuffer[i] = 0;
        }
    }

    public void addImageToDraw(TileSheet sheet, int offsetX, int offsetY,
                               int tileFromLeft, int tileFromTop, int renderLayer) {
        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        drawables.add(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void addImageToDraw(Image image, int offsetX, int offsetY, int renderLayer) {
        drawables.add(new OffsetImage(image, offsetX, offsetY, renderLayer));
    }

    public void draw() {
        OffsetImage drawable;
        while(!drawables.isEmpty()) {
            drawable = drawables.poll();
            drawImage(drawable.getImage(), drawable.getOffsetX(), drawable.getOffsetY());
        }
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
                    // Sets pixel if color does not have full alpha
                    if((((fontImage.getColor(x + font.getCharacterOffset(character), y)) >> 24) & 0xff) != 0) {
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

    public void drawTile(TileSheet sheet, int offsetX, int offsetY,
                         int tileFromLeft, int tileFromTop) {
        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        drawImage(image, offsetX, offsetY);
    }

    public void drawImage(Image image, int offsetX, int offsetY) {

        if(isOutsideOfCanvas(image, offsetX, offsetY)) return;

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
                        image.getColor(x, y)
                        );
            }
        }
    }

    public void setPixel(int x, int y, int value) {

        float alpha = ((value >> 24) & 0xff)/255f;
        if((x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight) ||
           alpha == 0) {
            return;
        }
        if(zBuffer[x + y * canvasWidth] > zDepth) {
            return;
        }
        if(alpha == 1) {
            pixels[x + y * canvasWidth] = value;
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

            pixels[x + y * canvasWidth] = (255 << 24 | blendedRed << 16 | blendedGreen << 8 | blendedBlue);
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
