public class WindowSettings {

    private int width;
    private int height;
    private float scale;
    private String title;

    public WindowSettings() {

        this.width = 320;
        this.height = 240;
        this.scale = 1f;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
