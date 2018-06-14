package game;

import engine.AbstractGame;
import engine.Renderer;
import engine.SimpleGameEngine;
import engine.gfx.Image;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameManager extends AbstractGame {

    private Image image;
    private final int TILE_SIZE = 64;

    public GameManager() throws IllegalArgumentException, IOException {
        image = new Image("/nekoSensei.png");
    }

    @Override
    public void update(SimpleGameEngine gameContainer, float deltaTime) {
    }

    @Override
    public void render(SimpleGameEngine gameContainer, Renderer renderer) {
        //TODO Consider offsetting to center mouse on image.

        renderer.drawImage(
                image,
                gameContainer.getInput().getMouseX() - TILE_SIZE/2,
                gameContainer.getInput().getMouseY() - TILE_SIZE/2
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
