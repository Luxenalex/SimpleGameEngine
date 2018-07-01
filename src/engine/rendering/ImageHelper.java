package engine.rendering;

import engine.gfx.Image;
import engine.gfx.OffsetImage;
import engine.gfx.TileSheet;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Manages the rendering of images.
 */
class ImageHelper extends RenderingHelper {

    private PriorityQueue<OffsetImage> drawables;

    ImageHelper(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){
        super(canvasWidth, canvasHeight, pixels, lightBlock);

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

    void addDrawable(OffsetImage drawable){
        drawables.add(drawable);
    }

    private OffsetImage getDrawable() {
        return drawables.poll();
    }

    void drawImages() {

        OffsetImage drawable;
        while(!drawables.isEmpty()) {
            drawable = getDrawable();
            drawImage(drawable.getImage(), drawable.getOffsetX(), drawable.getOffsetY());
        }
    }

    private void drawImage(Image image, int offsetX, int offsetY) {

        if(super.isOutsideOfCanvas(image.getWidth(), image.getHeight(), offsetX, offsetY)) {
            return;
        }

        int startX = 0;
        int startY = 0;

        if(offsetX < 0){
            startX = super.reduceAreaToDraw(startX, offsetX);
        }
        if(offsetY < 0){
            startY = super.reduceAreaToDraw(startY, offsetY);
        }

        int imageWidth = image.getWidth();
        if(imageWidth + offsetX >= canvasWidth){
            imageWidth = super.reduceAreaToDraw(imageWidth, imageWidth + offsetX - canvasWidth);
        }

        int imageHeight = image.getHeight();
        if (imageHeight + offsetY >= canvasHeight){
            imageHeight = super.reduceAreaToDraw(imageHeight, imageHeight + offsetY - canvasHeight);
        }

        for(int y = startY; y < imageHeight; y++) {
            for(int x = startX; x < imageWidth; x++) {
                super.setPixel(
                        x + offsetX,
                        y + offsetY,
                        image.getColor(x, y),
                        image.getLightBlock()
                );
            }
        }
    }

    void drawTile(TileSheet sheet, int offsetX, int offsetY,
                         int tileFromLeft, int tileFromTop) {
        Image image = sheet.getTile(tileFromLeft, tileFromTop);
        drawImage(image, offsetX, offsetY);
    }

}
