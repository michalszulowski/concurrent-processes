package concurrency;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedValueKeeperTest {
    private static SynchronizedValueKeeper<Integer> valueKeeper;
    private static Optional<Integer> pulledValue;

    @BeforeAll
    public static void initKeeper() {
        valueKeeper = new SynchronizedValueKeeper<>();
    }

    @Test
    public void testOneThreadSettingAndPulling() {
        givenValue(10);
        whenPulling();
        thenPulledValueShouldBe(Optional.of(10));
        whenPulling();
        thenPulledValueShouldBe(Optional.empty());
        givenValue(1);
        whenPulling();
        thenPulledValueShouldBe(Optional.of(1));
        whenPulling();
        thenPulledValueShouldBe(Optional.empty());
    }

    private void givenValue(int val) {
        valueKeeper.set(val);
    }

    private void whenPulling() {
        pulledValue = valueKeeper.pull();
    }

    private void thenPulledValueShouldBe(Optional<Integer> expected) {
        assertEquals(expected, pulledValue);
    }
}