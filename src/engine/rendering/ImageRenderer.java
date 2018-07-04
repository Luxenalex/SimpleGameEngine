package engine.rendering;

import engine.Position;
import engine.gfx.Image;
import engine.gfx.OffsetImage;
import engine.gfx.TileSheet;

import java.util.PriorityQueue;

/**
 * Manages the rendering of images.
 */
class ImageRenderer extends CanvasRenderer {

    private PriorityQueue<OffsetImage> images;

    ImageRenderer(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){
        super(canvasWidth, canvasHeight, pixels, lightBlock);

        images = new PriorityQueue<>(50, (image1, image2) -> {
            if(image1.getRenderLayer() < image2.getRenderLayer()) {
                return -1;
            }
            if(image1.getRenderLayer() > image2.getRenderLayer()) {
                return 1;
            }
            return 0;
        });
    }

    void addDrawable(OffsetImage drawable){
        images.add(drawable);
    }

    private OffsetImage getDrawable() {
        return images.poll();
    }

    void drawImages() {

        OffsetImage image;
        while(!images.isEmpty()) {
            image = getDrawable();
            drawImage(image.getImage(), image.getOffset());
        }
    }

    private void drawImage(Image image, Position offset) {
        int offsetX = offset.getX();
        int offsetY = offset.getY();

        if(super.isOutsideOfCanvas(image.getWidth(), image.getHeight(), offset)) {
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

    void drawTile(TileSheet sheet, Position offset, Position tilePosition) {
        Image image = sheet.getTile(tilePosition);
        drawImage(image, offset);
    }

}
