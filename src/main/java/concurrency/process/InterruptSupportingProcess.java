package concurrency.process;

import concurrency.CheckForPendingActionsOnInterruptHandler;
import concurrency.ExceptionHandler;

public abstract class InterruptSupportingProcess extends AbstractConcurrentProcess {
    private final ExceptionHandler<InterruptedException> interruptedExceptionExceptionHandler;

    public InterruptSupportingProcess(String name) {
        super(name);
        interruptedExceptionExceptionHandler = new CheckForPendingActionsOnInterruptHandler(this);
    }

    @Override
    public synchronized void notifyAboutChangedState() {
        runThread.interrupt();
    }

    @Override
    public ExceptionHandler<InterruptedException> getInterruptedExceptionHandler() {
        return interruptedExceptionExceptionHandler;
    }
}
