package engine;

import engine.input.Input;
import engine.window.Window;
import engine.window.WindowSettings;
import game.EngineLogger;

public class SimpleGameEngine implements Runnable {

    private Thread gameLoop;

    private boolean running;
    private final double TIME_STEP = 1.0 / 60.0;

    private WindowSettings windowSettings;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;


    public SimpleGameEngine(AbstractGame game){
        this.game = game;
    }

    public void start(String name) {
        windowSettings = new WindowSettings();
        window = new Window(windowSettings);
        renderer = new Renderer(window);
        input = new Input(window.getCanvas(), windowSettings);

        gameLoop = new Thread(this, name);
        gameLoop.start();
    }

    public void stop() {

    }

    @Override
    public void run() {

        if(running) return;

        running = true;
        window.setVisible(true);

        boolean render;
        GameTime gameTime = new GameTime();

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running){
            render = false;

            gameTime.updateTime();
            frameTime += gameTime.getPassedTime();

            while(gameTime.shouldUpdate(TIME_STEP)) {
                gameTime.decrementUnprocessedTime(TIME_STEP);
                render = true;

                game.update(this, (float)TIME_STEP);
                game.render(this, renderer);

                input.update();
                //TODO see if this can bee done better
                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if(render) {
                renderer.clear();
                game.render(this, renderer);
                renderer.drawText("FPS: " + fps, 0, 0, 0xFFFFFFFF);
                renderer.drawText("x: " + input.getMouseX() + " y: " + input.getMouseY(),
                                  0, renderer.getFontHeight(), 0xFFFFFFFF);
                window.update();
                frames++;
            }
            else {
                try {
                    Thread.sleep(1);
                }
                catch (InterruptedException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
        dispose();
    }

    public void dispose() {

    }

    public Input getInput() {
        return input;
    }
}
