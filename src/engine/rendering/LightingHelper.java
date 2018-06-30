package engine.rendering;

import engine.gfx.Light;
import engine.gfx.LightRequest;

import java.util.ArrayList;

/**
 * Manages rendering of lighting.
 */
class LightingHelper extends RenderingHelper {

    private ArrayList<LightRequest> lights;
    private int[] lightMap;
    private int ambientLighting = 0xFF6b6b6b;

    LightingHelper(int canvasWidth, int canvasHeight, int[] pixels, int[] lightBlock){
        super(canvasWidth, canvasHeight, pixels, lightBlock);

        lights = new ArrayList<>();
        lightMap = new int[pixels.length];
    }

    void clearLightMap(){
        for(int i = 0; i < pixels.length; i++){
            lightMap[i] = ambientLighting;
        }
    }

    void addLight(LightRequest lightRequest) {
        lights.add(lightRequest);
    }

    void drawLight() {

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
    }

    private void setLight(Light light, int offsetX, int offsetY){
        int radius = light.getRadius();
        int diameter = light.getDiameter();

        for(int i = 0; i <= light.getDiameter(); i++) {
            setLightLine(light, radius, radius, i, 0, offsetX, offsetY);
            setLightLine(light, radius, radius, i, diameter, offsetX, offsetY);
            setLightLine(light, radius, radius, 0, i, offsetX, offsetY);
            setLightLine(light, radius, radius, diameter, i, offsetX, offsetY);
        }
    }

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

    private void setLightMapPixel(int x, int y, int value){
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
}
