package concurrency;


import concurrency.process.ControllableProcess;
import concurrency.process.ExecutionController;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class UncaughtThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final ControllableProcess process;

    public UncaughtThreadExceptionHandler(ControllableProcess process) {
        this.process = process;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String exceptionDescription = printStackTraceToByteArrayAndGetStr(throwable);
        String message = String
                .format("Thread %s in process %s killed by an uncaught exception.\n" +
                        "Exception: %s", thread.getName(), process.getName(), exceptionDescription);
        System.out.println(message);
    }

    private String printStackTraceToByteArrayAndGetStr(Throwable throwable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        printExStackTraceTo(outputStream, throwable);
        return outputStream.toString();
    }

    private void printExStackTraceTo(ByteArrayOutputStream byteArrayOutputStream, Throwable ofThrowable) {
        try (PrintStream printStream = new PrintStream(byteArrayOutputStream, true)) {
            ofThrowable.printStackTrace(printStream);
        }
    }

    //TODO rewrite to unit test

    public static void main(String[] args) {
        Thread testThread = new Thread(() -> fun1());
        testThread.setName("TEST_THREAD");
        testThread.setUncaughtExceptionHandler(new UncaughtThreadExceptionHandler(new NamedProcess("TEST_PROCESS")));
        testThread.start();
    }

    private static void fun1() {
        fun2();
    }

    private static void fun2() {
        throw new RuntimeException("INFO");
    }

    @AllArgsConstructor
    private static class NamedProcess implements ControllableProcess {
        @Getter
        private final String name;

        @Override
        public void start() {}
        @Override
        public ExecutionController getExecutionController() {
            return null;
        }
    }
}
