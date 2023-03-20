package concurrency.process;

public interface ControllableProcess {
    void start();
    ExecutionController getExecutionController();
    String getName();
}
