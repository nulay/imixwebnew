package by.imix.arithmetic.services;

import by.imix.arithmetic.JupiterBase;
import by.imix.arithmetic.services.ArithmeticService.Operation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PrototypeService")
class TestArithmeticService extends JupiterBase {
    @Autowired
    ArithmeticService service;

    static Stream<Arguments> testCalculate() {
        return Stream.of( //
                Arguments.of(ArithmeticService.Operation.Subtract, 9, 3, 6), //
                Arguments.of(ArithmeticService.Operation.Add, 2, 3, 5), //
                Arguments.of(ArithmeticService.Operation.Multiply, 1, 0, 0), //
                Arguments.of(ArithmeticService.Operation.Subtract, 1, 0, 1), //
                Arguments.of(ArithmeticService.Operation.Divide, 0, 1, 0), //
                Arguments.of(ArithmeticService.Operation.Multiply, 3, 5, 15), //
                Arguments.of(ArithmeticService.Operation.Divide, 6, 2, 3), //
                Arguments.of(ArithmeticService.Operation.Add, 0, 1, 1) //
        );
    }

    @Test
    @DisplayName("getOperations")
    void testGetOperations() {
        final Collection<Operation> operations = service.getOperations();
        assertThat(operations).isNotNull().isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("calculate")
    void testCalculate(ArithmeticService.Operation operator, Integer left, Integer right, Integer expectedResult) {
        final Integer actualResult = service.calculate(operator, left, right);
        assertThat(actualResult).isNotNull().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("exceptions")
    void testExceptions() {
        assertThatThrownBy(() -> service.calculate(ArithmeticService.Operation.Divide, 1, 0)) //
                .isInstanceOf(ArithmeticException.class) //
                .hasMessage("/ by zero");

        assertThatThrownBy(() -> service.calculate(null, 0, 0)) //
                .isInstanceOf(NullPointerException.class) //
                .hasMessageContaining("must not be null");

        assertThatThrownBy(() -> service.calculate(ArithmeticService.Operation.Add, null, 0)) //
                .isInstanceOf(NullPointerException.class) //
                .hasMessageContaining("must not be null");

        assertThatThrownBy(() -> service.calculate(ArithmeticService.Operation.Multiply, 0, null)) //
                .isInstanceOf(NullPointerException.class) //
                .hasMessageContaining("must not be null");
    }
}
