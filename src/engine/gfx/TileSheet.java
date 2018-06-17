package engine.gfx;

public class TileSheet extends Image {

    public int tileWidth;
    public int tileHeight;

    public TileSheet(String path, int width, int height) {
        super(path);
        this.tileWidth = super.getWidth() > width ? width : super.getWidth();
        this.tileHeight = super.getHeight() > height ? height : super.getHeight();
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }
}
