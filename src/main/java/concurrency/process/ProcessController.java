package concurrency.process;


import concurrency.state.*;

public class ProcessController implements ExecutionController {
    private final ConcurrentProcess process;

    public ProcessController(ConcurrentProcess process) {
        this.process = process;
    }

    @Override
    public void start() {
        if (!process.isRunning()) {
            process.start();
        } else {
            setNewState(new RunningState(process));
        }
    }

    @Override
    public void kill() {
        setNewState(new KilledState(process));
    }

    @Override
    public void pause() {
        setNewState(new PausedState(process));
    }

    @Override
    public void unpause() {
        setNewState(new UnpausingState(process));
    }

    private void setNewState(StateWithOwner state) {
        process.setNewState(state);
        process.notifyAboutChangedState();
    }
}
