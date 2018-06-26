package game;

import engine.AbstractGame;
import engine.Renderer;
import engine.SimpleGameEngine;
import engine.audio.SoundClip;
import engine.gfx.Image;
import engine.gfx.TileSheet;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private Image image;
    private final int TILE_SIZE = 24;
    private SoundClip clip;

    float temp = 0;

    public GameManager() {
        image = new TileSheet("/spriteSheet.png", 24, 24);
        clip = new SoundClip("/audio/tempSound.wav");
        clip.changeVolume(0);
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
        //TODO Consider offsetting to center mouse on image.

        renderer.drawImage(
                image,
                gameContainer.getInput().getMouseX() - TILE_SIZE/2,
                gameContainer.getInput().getMouseY() - TILE_SIZE/2,
                (int)temp,
                0
        );
    }

    public static void main(String args[]){
        EngineLogger.initializeLogger("SimpleGameEngine.log");
        SimpleGameEngine gameEngine;

        gameEngine = new SimpleGameEngine(new GameManager());
        gameEngine.start("SimpleGameEngine");
    }
}
