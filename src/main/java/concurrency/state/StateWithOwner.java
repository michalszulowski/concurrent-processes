package concurrency.state;


import concurrency.process.ConcurrentProcess;

public abstract class StateWithOwner implements ProcessState {
    protected final ConcurrentProcess owner;
    private final String name;

    public StateWithOwner(ConcurrentProcess owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
