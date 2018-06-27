package engine.gfx;

public class OffsetImage {
    private Image image;
    private int offsetX;
    private int offsetY;
    private int renderLayer;

    public OffsetImage(Image image, int offsetX, int offsetY, int renderLayer) {
        this.image = image;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.renderLayer = renderLayer;
    }

    public Image getImage() {
        return image;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getRenderLayer() {
        return renderLayer;
    }
}
