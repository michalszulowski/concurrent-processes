package concurrency.state;

public interface ProcessState {
    void executeOnActivation();
    String getName();
}
