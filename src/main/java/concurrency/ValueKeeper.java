package concurrency;

import java.util.Optional;

public interface ValueKeeper<T> {
    Optional<T> pull();
    void set(T newVal);
}
