package engine.gfx;

public class Light {

    private int radius;
    private int diameter;
    private int color;
    private int[] lightMap;

    public Light(int radius, int color) {
        this.radius = radius;
        this.diameter = radius * 2;
        this.color = color;
        lightMap = new int[diameter * diameter];

        int red = ((color >> 16) & 0xFF);
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        System.out.println(Integer.toHexString(red << 16));
        System.out.println(Integer.toHexString(green));
        System.out.println(Integer.toHexString(blue));


        for(int y = 0; y < diameter; y++) {
            for (int x = 0; x < diameter; x++) {

                double distanceFromCenter = Math.sqrt(
                        (x - radius) * (x - radius) + (y - radius) * (y - radius)
                );

                if(distanceFromCenter < radius) {
                    double power = 1 - (distanceFromCenter / radius);
                    lightMap[x + y * diameter] = (int)(red * power) << 16 |
                            (int)(green * power) << 8 | (int)(blue * power);

                    /*System.out.println( Integer.toHexString((int)(red * power) << 16 |
                            (int)(green * power) << 8 | (int)(blue * power)));
                           */
                }
                else {
                    lightMap[x + y * diameter] = 0;
                }
            }
        }
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.diameter = radius * 2;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getLight(int x, int y) {
        if(x < 0 || x >= diameter || y < 0 || y >= diameter) {
            return 0;
        }
        return lightMap[x + y * diameter];
    }

    public int[] getLightMap() {
        return lightMap;
    }

    public void setLightMap(int[] lightMap) {
        this.lightMap = lightMap;
    }
}