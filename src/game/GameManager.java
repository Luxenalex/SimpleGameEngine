package game;

import engine.AbstractGame;
import engine.Position;
import engine.rendering.Renderer;
import engine.SimpleGameEngine;
import engine.audio.SoundClip;
import engine.gfx.Image;
import engine.gfx.Light;
import engine.gfx.TileSheet;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private TileSheet link;
    private final int TILE_SIZE = 24;
    private SoundClip clip;

    private TileSheet ghostLink;
    private Image background;

    private Light light;

    float temp = 0;

    public GameManager() {
        link = new TileSheet("/spriteSheet.png", 24, 24);
        link.setLightBlock(Light.FULL);
        clip = new SoundClip("/audio/tempSound.wav");
        clip.changeVolume(0);

        background = new Image("/background.png");
        ghostLink = new TileSheet("/alphaSpriteSheet.png", 24, 24);
        light = new Light(100, 0xFFeeee00);
    }

    @Override
    public void update(SimpleGameEngine gameContainer, float deltaTime) {

        // For testing animation.
        temp += deltaTime * 12;
        if (temp > 7) {
            temp = 0;
        }

        if (gameContainer.getInput().isKeyDown(KeyEvent.VK_A)){
            clip.play();
        }
    }

    @Override
    public void render(SimpleGameEngine gameContainer, Renderer renderer) {
        //TODO Consider offsetting to center mouse on link.
        renderer.addImageToDraw(
                ghostLink,
                new Position(gameContainer.getInput().getMouseX() - TILE_SIZE / 2,
                             gameContainer.getInput().getMouseY() - TILE_SIZE/2),
                new Position((int)temp, 0),
                3
        );
        renderer.addImageToDraw(
                link,
                new Position(100, 150),
                new Position((int)temp, 0),
                3
        );
        renderer.addImageToDraw(background, new Position(0, 0), 0);
        renderer.drawImages();
        renderer.drawTile(
                ghostLink,
                new Position(100, 129),
                new Position(1, 0)
        );
        renderer.drawFilledRectangle(
                new Position(20, 29),
                50,
                90,
                0xffffccdd
        );
        renderer.addLightToDraw(
                light,
                new Position(
                        gameContainer.getInput().getMouseX() - 7,
                        gameContainer.getInput().getMouseY()
                )
        );
        renderer.addLightToDraw(
                new Light(75, 0xFF0000FF),
                new Position(200, 150)
        );
        renderer.drawLight();
        renderer.drawRectangle(new Position(10, 10), 300, 200, 0xff00cccc);
    }

    public static void main(String args[]){
        EngineLogger.initializeLogger("SimpleGameEngine.log");
        SimpleGameEngine gameEngine;

        gameEngine = new SimpleGameEngine(new GameManager());
        gameEngine.start("SimpleGameEngine");
    }
}
