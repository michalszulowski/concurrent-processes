package concurrency.process;

import static org.junit.jupiter.api.Assertions.*;

class InterruptSupportingProcessTest {

    public void testInterrupt() {
        WaitingProcess process = new WaitingProcess();
        process.start();
        long time = System.currentTimeMillis();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        process.getExecutionController().kill();
        //TODO finish
    }



    private static class WaitingProcess extends InterruptSupportingProcess {
        private final static int waitTime = 1000;
        private long destructionTime;

        public WaitingProcess() {
            super("WAITING PROCESS");
        }

        @Override
        protected void performMainLoopBody() {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                getInterruptedExceptionHandler().handle(e);
            }
        }

        @Override
        protected void destruct() {
            super.destruct();
            destructionTime = System.currentTimeMillis();
        }
    }
}
