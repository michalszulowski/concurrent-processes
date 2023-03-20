package concurrency.state;


import concurrency.process.ConcurrentProcess;
import concurrency.process.ProcessKilledException;

public class KilledState extends StateWithOwner {
    public KilledState(ConcurrentProcess owner) {
        super(owner, "KILLED");
    }

    @Override
    public void executeOnActivation() {
        throw new ProcessKilledException();
    }
}
