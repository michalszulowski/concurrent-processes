package concurrency.process;

/**
 * Gives synchronized methods for controlling thread.
 */
public interface ExecutionController {
    void start();
    void kill();
    void pause();
    void unpause();
}
