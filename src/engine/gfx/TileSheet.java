package engine.gfx;

import engine.Position;

public class TileSheet extends Image {

    public int tileWidth;
    public int tileHeight;

    public TileSheet(String path, int tileWidth, int tileHeight) {
        super(path);
        this.tileWidth = super.getWidth() > tileWidth ? tileWidth : super.getWidth();
        this.tileHeight = super.getHeight() > tileHeight ? tileHeight : super.getHeight();
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public Image getTile(Position tile) {

        int[] pixels = new int[tileWidth * tileHeight];

        int startY = tileHeight * tile.getY();
        int endY = startY + tileHeight;
        int startX = tileWidth * tile.getX();
        int endX = startX + tileWidth;

        int index = 0;
        for(int y = startY; y < endY ; y++) {
            for(int x = startX; x < endX; x++) {

                pixels[index] = super.getColor(x, y);
                index++;
            }
        }

        return new Image(pixels, tileWidth, tileHeight);

    }
}
