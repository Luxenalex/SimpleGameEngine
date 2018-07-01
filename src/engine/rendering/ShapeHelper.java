package engine.rendering;

import engine.gfx.Light;

/**
 * Manages rendering of primitive shapes.
 */
class ShapeHelper extends RenderingHelper {

    ShapeHelper(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){
        super(canvasWidth, canvasHeight, pixels, lightBlock);
    }

    public void drawRectangle(int offsetX, int offsetY, int width, int height, int color){
        for(int y = 0; y <= height; y++){
            super.setPixel(offsetX, y + offsetY, color, Light.NONE);
            super.setPixel(offsetX + width, y + offsetY, color, Light.NONE);
        }

        for(int x = 0; x <= width; x++){
            super.setPixel(x + offsetX, offsetY, color, Light.NONE);
            super.setPixel(x + offsetX, offsetY + height, color, Light.NONE);
        }
    }

    public void drawFilledRectangle(int offsetX, int offsetY, int width, int height, int color){

        if(super.isOutsideOfCanvas(width, height, offsetX, offsetY)){
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
