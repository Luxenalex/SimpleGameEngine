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

        while(running){
            currentTime = System.nanoTime() / BILLION;
            passedTime = currentTime - lastTime;
            lastTime = currentTime;
            unprocessedTime += passedTime;

            while(unprocessedTime >= FPS_LIMIT) {
                unprocessedTime -= FPS_LIMIT;
                System.out.print(".");
                //TODO Update game
            }

            if(render) {
                //TODO: render game
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
