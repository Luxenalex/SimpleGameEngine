package engine;

public class GameTime {
    final double BILLION = 1000000000.0;

    private double currentTime = 0;
    private double lastTime = System.nanoTime() / BILLION;
    private double passedTime = 0;
    private double unprocessedTime = 0;

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

    public boolean shouldUpdate(final double timeStep) {
        return unprocessedTime >= timeStep;
    }

    public void decrementUnprocessedTime(final double value) {
        unprocessedTime -= value;
    }
}
