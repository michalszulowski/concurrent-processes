package concurrency.process;


import concurrency.SynchronizedValueKeeper;
import concurrency.state.IdleState;
import concurrency.state.ProcessState;

public abstract class AbstractConcurrentProcess extends AbstractControllableProcess {
    private volatile ProcessState currentState;
    private final SynchronizedValueKeeper<ProcessState> setStateKeeper;
    private final ExecutionController executionController;

    public AbstractConcurrentProcess(String name) {
        super(name);
        setStateKeeper = new SynchronizedValueKeeper<>();
        executionController = new ProcessController(this);
        activateNewState(new IdleState(this));
    }

    @Override
    public void checkForPendingControlActions() {
        setStateKeeper.pull()
                .ifPresent(this::activateNewState);
    }

    @Override
    public ProcessState getState() {
        return currentState;
    }

    @Override
    public synchronized boolean isRunning() {
        return super.isRunning();
    }

    @Override
    public void setNewState(ProcessState state) {
        setStateKeeper.set(state);
    }

    @Override
    public ExecutionController getExecutionController() {
        return executionController;
    }

    protected void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            getInterruptedExceptionHandler().handle(ex);
        }
    }

    private void activateNewState(ProcessState state) {
        currentState = state;
        state.executeOnActivation();
    }
}
