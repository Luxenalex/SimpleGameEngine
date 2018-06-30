package engine;

import engine.rendering.Renderer;

public abstract class AbstractGame {

    public abstract void update(SimpleGameEngine gameContainer, float deltaTime);
    public abstract void render(SimpleGameEngine gameContainer, Renderer renderer);

}