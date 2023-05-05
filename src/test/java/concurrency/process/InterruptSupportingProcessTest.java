package concurrency.process;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterruptSupportingProcessTest {

    @Test
    public void testInterrupt() {
        WaitingProcess process = new WaitingProcess();
        process.start();
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        process.getExecutionController().kill();
        process.waitUntilFinished();
        long totalTime = System.currentTimeMillis() - start;
        System.out.println(totalTime);
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
