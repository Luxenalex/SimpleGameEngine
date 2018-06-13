package game;

import engine.AbstractGame;
import engine.Renderer;
import engine.SimpleGameEngine;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    public GameManager(){

    }

    @Override
    public void update(SimpleGameEngine gameContainer, float deltaTime) {
        if(gameContainer.getInput().isKeyDown(KeyEvent.VK_A)){
            System.out.println("A down!");
        }
    }

    @Override
    public void render(SimpleGameEngine gameContainer, Renderer renderer) {

    }

    public static void main(String args[]){
        SimpleGameEngine gameEngine = new SimpleGameEngine(new GameManager());
        gameEngine.start();
    }
}
