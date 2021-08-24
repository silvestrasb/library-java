package library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void testAddition() {
        // given
        int x = 5;
        int y = 9;

        // when
        int result = calculator.add(x, y);

        // then
        assertEquals(14, result);
    }

    class Calculator {
        int add(int x, int y) {
            return x + y;
        }
    }
}
