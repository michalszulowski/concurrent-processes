package concurrency.state;


import concurrency.process.ConcurrentProcess;

public class RunningState extends StateWithOwner {

    public RunningState(ConcurrentProcess owner) {
        super(owner, "RUNNING");
    }

    @Override
    public void executeOnActivation() {
        if (!owner.isRunning()) {
            owner.start();
        }
    }
}
