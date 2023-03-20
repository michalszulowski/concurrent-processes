package concurrency.process;


import concurrency.ExceptionHandler;
import concurrency.state.ProcessState;

/**
 * Represents runnable thread.
 */
public interface ConcurrentProcess extends ControllableProcess {
    ProcessState getState();
    void setNewState(ProcessState state);
    void notifyAboutChangedState();
    void checkForPendingControlActions();
    boolean isRunning();
    ExceptionHandler<InterruptedException> getInterruptedExceptionHandler();
}
