package engine.gfx;

import java.io.IOException;

public class TileSheet extends Image {

    public int tileWidth;
    public int tileHeight;

    public TileSheet(String path, int width, int height)
            throws IOException, IllegalArgumentException {
            super(path);
            this.tileWidth = width;
            this.tileHeight = height;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }
}
