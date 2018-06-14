package engine.window;

public class WindowSettings {

    private int width;
    private int height;
    private float scale;
    private String title;

    public WindowSettings() {

        this.width = 320;
        this.height = 240;
        this.scale = 3f;
        this.title = "Best Engine 0.1";
    }

    public WindowSettings(int width, int height, float scale, String title) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.title = title;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public String getTitle() {
        return title;
    }
}
