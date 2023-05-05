package concurrency;

import concurrency.process.ConcurrentProcess;

public class CheckForPendingActionsOnInterruptHandler implements ExceptionHandler<InterruptedException> {
    private final ConcurrentProcess owner;

    public CheckForPendingActionsOnInterruptHandler(ConcurrentProcess owner) {
        this.owner = owner;
    }

    @Override
    public void handle(InterruptedException exception) {
        owner.checkForPendingControlActions();
    }
}
