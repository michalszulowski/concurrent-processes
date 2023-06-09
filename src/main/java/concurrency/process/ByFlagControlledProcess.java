package concurrency.process;

import concurrency.ExceptionHandler;
import concurrency.KillOnInterruptHandler;


//RENAMED FROM SimpleConcurrentProcess
public abstract class ByFlagControlledProcess extends AbstractConcurrentProcess {
    private final ExceptionHandler<InterruptedException> interruptedExceptionExceptionHandler;

    public ByFlagControlledProcess(String name) {
        super(name);
        interruptedExceptionExceptionHandler = new KillOnInterruptHandler(this);
    }

    @Override
    public synchronized void notifyAboutChangedState() {
        // No notification supported, process simply changes if new state has been set.
    }

    @Override
    public ExceptionHandler<InterruptedException> getInterruptedExceptionHandler() {
        return interruptedExceptionExceptionHandler;
    }
}
