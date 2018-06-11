public class SimpleGameEngine implements Runnable {

    private Thread gameLoop;

    private boolean running;
    private final double FPS_LIMIT = 1.0/60.0;

    private WindowSettings windowSettings;
    private Window window;

    public SimpleGameEngine(){

    }

    public void start() {
        windowSettings = new WindowSettings();
        window = new Window(windowSettings);

        gameLoop = new Thread(this);
        gameLoop.run();
    }

    public void stop() {

    }

    public void run() {
        running = true;

        final double BILLION = 1000000000.0;
        boolean render = false;

        GameTime gameTime = new GameTime();

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running){
            render = false;

            gameTime.updateTime();
            frameTime += gameTime.getPassedTime();

            //TODO Update game
            while(gameTime.shouldUpdate(FPS_LIMIT)) {
                gameTime.decrementUnprocessedTime(FPS_LIMIT);
                render = true;
                System.out.println("FPS: " + fps);

                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            //TODO: render game
            if(render) {
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
