package engine;

import engine.input.Input;
import engine.window.Window;
import engine.window.WindowSettings;

public class SimpleGameEngine implements Runnable {

    private Thread gameLoop;

    private boolean running;
    private final double TIME_STEP = 1.0 / 1.0;

    private WindowSettings windowSettings;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;

    public SimpleGameEngine(AbstractGame game){
        this.game = game;
    }

    public void start() {
        windowSettings = new WindowSettings();
        window = new Window(windowSettings);
        renderer = new Renderer(window);
        input = new Input(window);

        gameLoop = new Thread(this);
        gameLoop.run();
    }

    public void stop() {

    }

    public void run() {
        running = true;

        boolean render;
        GameTime gameTime = new GameTime();

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running){
            render = false;

            gameTime.updateTime();
            frameTime += gameTime.getPassedTime();

            //TODO Update game
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

            //TODO: render game
            if(render) {
                renderer.clear();
                frames++;
                System.out.println("rendering");
                window.update();
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
