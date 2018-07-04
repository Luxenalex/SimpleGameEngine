package engine.gfx;

import engine.Position;

public class OffsetImage {
    private Image image;
    private Position offset;
    private int renderLayer;

    public OffsetImage(Image image, Position offset, int renderLayer) {
        this.image = image;
        this.offset = offset;
        this.renderLayer = renderLayer;
    }

    public Image getImage() {
        return image;
    }

    public Position getOffset() {
        return offset;
    }

    public int getRenderLayer() {
        return renderLayer;
    }
}
