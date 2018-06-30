package engine.rendering;

import engine.gfx.Image;
import engine.gfx.OffsetImage;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by lux on 6/29/18.
 */
public class DrawableHandler {

    protected PriorityQueue<OffsetImage> drawables;

    private int canvasWidth;
    private int canvasHeight;
    protected int pixels[];
    private int[] lightBlock;

    public DrawableHandler(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.pixels = pixels;
        this.lightBlock = lightBlock;

        drawables = new PriorityQueue<OffsetImage>(50, new Comparator<OffsetImage>() {
            @Override
            public int compare(OffsetImage image1, OffsetImage image2) {
                if(image1.getRenderLayer() < image2.getRenderLayer()) {
                    return -1;
                }
                if(image1.getRenderLayer() > image2.getRenderLayer()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    protected void addDrawable(OffsetImage drawable){
        drawables.add(drawable);
    }

    public PriorityQueue<OffsetImage> getDrawables() {
        return drawables;
    }

    public OffsetImage getDrawable() {
        return drawables.poll();
    }

    public void drawImages() {

        OffsetImage drawable;
        while(!drawables.isEmpty()) {
            drawable = getDrawable();
            drawImage(drawable.getImage(), drawable.getOffsetX(), drawable.getOffsetY());
        }
    }

    private boolean isOutsideOfCanvas(int width, int height, int offsetX, int offsetY) {
        return offsetX < -width || offsetY < -height ||
                offsetX >= canvasWidth || offsetY >= canvasHeight;
    }

    private int reduceAreaToDraw(int start, int offset) {
        return start - offset;
    }

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

            this.pixels[x + y * canvasWidth] = (blendedRed << 16 | blendedGreen << 8 | blendedBlue);
        }
    }

    public void setLightBlock(int x, int y, int value){
        if(isOutsideOfCanvas(x, y)){
            return;
        }

        lightBlock[x + y * canvasWidth] = value;
    }

    //TODO move to superclass???
    private boolean isOutsideOfCanvas(int x, int y) {
        return x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight;
    }

}
