public class SimpleGameEngine implements Runnable {

    private Thread gameLoop;

    private boolean running;
    private final double TIME_STEP = 1.0 / 60.0;

    private WindowSettings windowSettings;
    private Window window;
    private Renderer renderer;

    public SimpleGameEngine(){
    }

    public void start() {
        windowSettings = new WindowSettings();
        window = new Window(windowSettings);
        renderer = new Renderer(window);

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
                System.out.println("FPS: " + fps);

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
                window.update();
                frames++;
            }
            else {
                try {
                    Thread.sleep(1);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    public void dispose() {

    }

    public static void main(String args[]) {
        SimpleGameEngine gameContainer = new SimpleGameEngine();
        gameContainer.start();
    }
}
