package concurrency.process;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterruptSupportingProcessTest {
    private static WaitingProcess process;
    private static long totalTime;

    @Test
    public void testInterruptingWait() {
        givenProcessWaiting(1000);
        whenTryingToKillItDuringSleepAfter(75);
        thenItShouldFinishNoLongerThan(100);
    }

    private void givenProcessWaiting(long time) {
        process = new WaitingProcess(time);
    }

    private void whenTryingToKillItDuringSleepAfter(long millis) {
        process.start();
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        process.getExecutionController().kill();
        process.waitUntilFinished();
        totalTime = System.currentTimeMillis() - start;
    }

    private void thenItShouldFinishNoLongerThan(long maxMillis) {
        assertTrue(totalTime <= maxMillis);
    }


    private static class WaitingProcess extends InterruptSupportingProcess {
        private long waitTime;

        public WaitingProcess(long waitTime) {
            super("WAITING PROCESS");
            this.waitTime = waitTime;
        }

        @Override
        protected void performMainLoopBody() {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                getInterruptedExceptionHandler().handle(e);
            }
        }
    }
}
