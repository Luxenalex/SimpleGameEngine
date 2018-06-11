public class SimpleGameEngine implements Runnable {

    private Thread gameLoop;
    private boolean running;
    private final double FPS_LIMIT = 1.0/60.0;

    public SimpleGameEngine(){

    }

    public void start() {
        gameLoop = new Thread(this);
        gameLoop.run();
    }

    public void stop() {

    }

    public void run() {
        running = true;

        final double BILLION = 1000000000.0;
        boolean render = false;

        double currentTime = 0;
        double lastTime = System.nanoTime() / BILLION;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running){
            render = false;

            currentTime = System.nanoTime() / BILLION;
            passedTime = currentTime - lastTime;
            lastTime = currentTime;
            unprocessedTime += passedTime;

            frameTime += passedTime;


            //TODO Update game
            while(unprocessedTime >= FPS_LIMIT) {
                unprocessedTime -= FPS_LIMIT;
                render = true;

                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("FPS: " + fps);
                }
            }

            //TODO: render game
            if(render) {
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
