package concurrency.process;


import concurrency.UncaughtThreadExceptionHandler;

public abstract class AbstractControllableProcess implements ConcurrentProcess {
    protected final Thread runThread;
    protected final String name;
    private volatile boolean running;
    private volatile boolean finished;

    public AbstractControllableProcess(String name) {
        runThread = constructThread(name);
        this.name = name;
        running = false;
        finished = false;
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

    @Override
    public synchronized void waitUntilFinished() {
        if (finished) {
            return;
        }
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void performMainLoopBody();

    protected void destruct() {}

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

    private synchronized void kill() {
        running = false;
        destruct();
        finished = true;
        notifyAll();
    }
}
