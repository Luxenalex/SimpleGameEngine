package engine.gfx;

public class LightRequest {

    private Light light;
    private int centerX;
    private int centerY;

    public LightRequest(Light light, int centerX, int centerY) {
        this.light = light;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public Light getLight() {
        return light;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }
}
