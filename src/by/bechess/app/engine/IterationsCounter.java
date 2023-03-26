package by.bechess.app.engine;

public class IterationsCounter {
    private int iterationsCount;
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public String getIterationTime() {
        double result = (double) (endTime - startTime) / 1_000_000_000;
        result = Math.round(result * 1000.0) / 1000.0;
        return String.valueOf(result);
    }

    public void addIteration() {
        iterationsCount++;
    }

    public int getIterationsCount() {
        return iterationsCount;
    }
}
