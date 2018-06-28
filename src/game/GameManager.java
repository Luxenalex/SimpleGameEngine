package game;

import engine.AbstractGame;
import engine.Renderer;
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
        clip = new SoundClip("/audio/tempSound.wav");
        clip.changeVolume(0);

        background = new Image("/background.png");
        ghostLink = new TileSheet("/alphaSpriteSheet.png", 24, 24);
        light = new Light(100, 0xFF00FF00);
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
                gameContainer.getInput().getMouseX() - TILE_SIZE/2,
                gameContainer.getInput().getMouseY() - TILE_SIZE/2,
                (int)temp,
                0,
                3
        );
        renderer.addImageToDraw(background, 0, 0, 0);

        renderer.drawLight(light, gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY());

        renderer.draw();

        //renderer.drawFilledRectangle(gameContainer.getInput().getMouseX() - 16,
           //     gameContainer.getInput().getMouseY() - 16, 32, 32, 0xffffccff);
    }

    public static void main(String args[]){
        EngineLogger.initializeLogger("SimpleGameEngine.log");
        SimpleGameEngine gameEngine;

        gameEngine = new SimpleGameEngine(new GameManager());
        gameEngine.start("SimpleGameEngine");
    }
}
