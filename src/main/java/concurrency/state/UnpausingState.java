package concurrency.state;


import concurrency.process.ConcurrentProcess;

public class UnpausingState extends StateWithOwner {

    public UnpausingState(ConcurrentProcess owner) {
        super(owner, "UNPAUSING");
    }

    @Override
    public void executeOnActivation() {
        owner.getExecutionController().start();
    }
}
