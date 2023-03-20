package concurrency.process;

import command.Command;
import command.factory.CommandFactory;
import command.factory.DictionaryCommandFactory;
import command.factory.NoSuchCommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class allowing to manually test controlling ConcurrentProcess. It fetches commands from terminal and opens
 * new terminal with process output. Supports only Linux.
 */
class TimePrintingProcessManualTest {
    private boolean running;
    private ConcurrentProcess timePrinter;
    private CommandFactory commandFactory;
    private BufferedReader lineReader;

    public TimePrintingProcessManualTest() {
        running = true;
        timePrinter = new TimePrintingProcess();
        commandFactory = buildCommandFactory(timePrinter);
        lineReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        timePrinter.getExecutionController().start();
        startListeningForCommands();
    }

    private CommandFactory buildCommandFactory(ConcurrentProcess process) {
        var commandFactory = new DictionaryCommandFactory();
        commandFactory.addCommand("start", args -> (() -> process.getExecutionController().start()));
        commandFactory.addCommand("pause", args -> (() -> process.getExecutionController().pause()));
        commandFactory.addCommand("unpause", args -> (() -> process.getExecutionController().unpause()));
        commandFactory.addCommand("kill", args -> (() -> {
            process.getExecutionController().kill();
            running = false;
        } ));
        return commandFactory;
    }

    private void startListeningForCommands() {
        while (running) {
            String input = waitForLineAndRead();
            tryToInvokeCommand(input);
        }
    }

    private String waitForLineAndRead() {
        try {
            return lineReader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void tryToInvokeCommand(String input) {
        try {
            Command command = commandFactory.getCommand(input);
            command.invoke();
        } catch (NoSuchCommandException ex) {
            System.out.println("Wrong command: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new TimePrintingProcessManualTest().run();
    }
}