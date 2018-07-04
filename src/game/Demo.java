package game;

import engine.AbstractGame;
import engine.SimpleGameEngine;
import engine.audio.SoundClip;
import engine.gfx.Light;
import engine.gfx.TileSheet;
import engine.rendering.Renderer;

import java.util.concurrent.ThreadLocalRandom;

public class Demo extends AbstractGame {
    private TileSheet hero;
    private TileSheet terrain;
    private final int TILE_SIZE = 24;
    private SoundClip clip;
    private Light light;

    private float temp = 0;
    private int[][] placement;

    public Demo() {
        hero = new TileSheet("/spriteSheet.png", 24, 24);
        hero.setLightBlock(Light.FULL);

        terrain = new TileSheet("/demoTerrain.png", 16, 16);
        terrain.setLightBlock(Light.NONE);

        clip = new SoundClip("/audio/tempSound.wav");
        clip.changeVolume(0);
        light = new Light(100, 0xFFeeee00);

        placement = new int[300][2];
        for(int[] aPlacement : placement) {
            aPlacement[0] = ThreadLocalRandom.current().nextInt(0, 3);
            aPlacement[1] = 0;
        }
    }

    @Override
    public void update(SimpleGameEngine gameContainer, float deltaTime) {
        temp += deltaTime * 12;
        if (temp > 7) {
            temp = 0;
        }
    }


    @Override
    public void render(SimpleGameEngine gameContainer, Renderer renderer) {

        renderer.setBackground(terrain, placement);
        renderer.addImageToDraw(
                hero,
                gameContainer.getInput().getMouseX() - TILE_SIZE/2,
                gameContainer.getInput().getMouseY() - TILE_SIZE/2,
                (int)temp,
                0,
                3
        );
        renderer.drawImages();
        renderer.addLightToDraw(light, gameContainer.getInput().getMouseX() - 7, gameContainer.getInput().getMouseY());
        renderer.drawLight();
    }

    public static void main(String args[]){
        EngineLogger.initializeLogger("SimpleGameEngine.log");
        SimpleGameEngine gameEngine;

        gameEngine = new SimpleGameEngine(new Demo());
        gameEngine.start("SimpleGameEngine");
    }
}
