package concurrency;

public interface ExceptionHandler<T extends Throwable> {
    void handle(T exception);
}
