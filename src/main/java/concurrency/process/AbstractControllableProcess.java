package concurrency.process;


import concurrency.UncaughtThreadExceptionHandler;

public abstract class AbstractControllableProcess implements ConcurrentProcess {
    private final Thread runThread;
    private final String name;
    private boolean running;

    public AbstractControllableProcess(String name) {
        runThread = constructThread(name);
        this.name = name;
        running = false;
    }

    @Override
    public void start() {
        running = true;
        runThread.start();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public String getName() {
        return name;
    }

    protected abstract void performMainLoopBody();

    //TODO rename maybe something like finalize
    protected void freeResources() {}

    private Thread constructThread(String name) {
        Thread thread = new Thread(this::startMainLoop);
        thread.setName(name);
        thread.setUncaughtExceptionHandler(new UncaughtThreadExceptionHandler(this));
        return thread;
    }

    private void startMainLoop() {
        while (running) {
            try {
                performMainLoopBody();
                checkForPendingControlActions();
            } catch (ProcessKilledException ex) {
                kill();
            }
        }
    }

    private void kill() {
        running = false;
        freeResources();
    }
}
