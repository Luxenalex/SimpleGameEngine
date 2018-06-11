public class GameTime {
    final double BILLION = 1000000000.0;

    double currentTime = 0;
    double lastTime = System.nanoTime() / BILLION;
    double passedTime = 0;
    double unprocessedTime = 0;

    public GameTime() {

    }

    public void updateTime() {
        currentTime = System.nanoTime() / BILLION;
        passedTime = currentTime - lastTime;
        lastTime = currentTime;
        unprocessedTime += passedTime;
    }

    public double getPassedTime() {
        return passedTime;
    }

    public boolean shouldUpdate(final double FPS_LIMIT) {
        return unprocessedTime >= FPS_LIMIT;
    }

    public void decrementUnprocessedTime(final double value) {
        unprocessedTime -= value;
    }
}
