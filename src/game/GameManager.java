package game;

import engine.AbstractGame;
import engine.Renderer;
import engine.SimpleGameEngine;
import engine.gfx.Image;
import engine.gfx.TileSheet;

import java.io.IOException;

public class GameManager extends AbstractGame {

    private Image image;
    private final int TILE_SIZE = 24;

    float temp = 0;

    public GameManager() throws IllegalArgumentException, IOException {
        image = new Image("/spriteSheet.png");
    }

    @Override
    public void update(SimpleGameEngine gameContainer, float deltaTime) {

        // For testing animation.
        temp += deltaTime * 20;
        if(temp > 3) {
            temp = 0;
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
        SimpleGameEngine gameEngine;
        try {
            gameEngine = new SimpleGameEngine(new GameManager());
            gameEngine.start("SimpleGameEngine");
        }
        catch (IOException error) {
            System.err.println("Error: " + error.getMessage());
        }
    }
}
