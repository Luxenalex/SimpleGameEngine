package engine.gfx;

import engine.Position;

public class LightRequest {

    private Light light;
    private Position center;

    public LightRequest(Light light, Position center) {
        this.light = light;
        this.center = center;
    }

    public Light getLight() {
        return light;
    }

    public int getCenterX() {
        return center.getX();
    }

    public int getCenterY() {
        return center.getY();
    }
}
