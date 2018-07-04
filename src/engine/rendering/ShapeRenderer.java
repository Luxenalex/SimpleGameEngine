package engine.rendering;

import engine.Position;
import engine.gfx.Light;

/**
 * Manages rendering of primitive shapes.
 */
class ShapeRenderer extends CanvasRenderer {

    ShapeRenderer(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){
        super(canvasWidth, canvasHeight, pixels, lightBlock);
    }

    public void drawRectangle(Position offset, int width, int height, int color){
        int offsetX = offset.getX();
        int offsetY = offset.getY();

        for(int y = 0; y <= height; y++){
            super.setPixel(offsetX, y + offsetY, color, Light.NONE);
            super.setPixel(offsetX + width, y + offsetY, color, Light.NONE);
        }

        for(int x = 0; x <= width; x++){
            super.setPixel(x + offsetX, offsetY, color, Light.NONE);
            super.setPixel(x + offsetX, offsetY + height, color, Light.NONE);
        }
    }

    public void drawFilledRectangle(Position offset, int width, int height, int color){
        int offsetX = offset.getX();
        int offsetY = offset.getY();

        if(super.isOutsideOfCanvas(width, height, offset)){
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

        if(width + offsetX >= canvasWidth){
            width = super.reduceAreaToDraw(width, width + offsetX - canvasWidth);
        }
        if (height + offsetY >= canvasHeight){
            height = super.reduceAreaToDraw(height, height + offsetY - canvasHeight);
        }

        for(int y = startY; y <= height; y++){
            for (int x = startX; x <= width; x++){
                super.setPixel(x + offsetX, y + offsetY, color, Light.NONE);
            }
        }
    }
}
