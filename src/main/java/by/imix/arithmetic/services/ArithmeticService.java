package by.imix.arithmetic.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.IntBinaryOperator;

/**
 * Пример Spring-сервиса, который выполняет арифметические вычисления.
 */
@Slf4j
@Service
public class ArithmeticService {
    /**
     * Возвращает список доступных операций.
     *
     * @return список доступных операций
     */
    public Collection<Operation> getOperations() {
        if (log.isDebugEnabled()) {
            log.debug("getOperations()");
        }

        final List<Operation> result = List.of(Operation.values());
        if (log.isDebugEnabled()) {
            log.debug("getOperations(): result = {}", result);
        }

        return result;
    }

    /**
     * Расчитывает результат выполнения операции {@code operator} над операндами {@code left} и {@code right}.
     *
     * @param operator операция, которую необходимо посчитать
     * @param left     левый операнд операции
     * @param right    правый операнд операции
     * @return результат вычисления
     * @throws NullPointerException если любой из параметров имеет значение {@code null}
     * @see Operation#calculate(int, int)
     */
    public Integer calculate(Operation operator, Integer left, Integer right) throws NullPointerException {
        if (log.isDebugEnabled()) {
            log.debug("calculate(): operator = {}, left = {}, right = {}", operator, left, right);
        }

        Objects.requireNonNull(operator, () -> "operator operand must not be null");
        Objects.requireNonNull(left, () -> "left operand must not be null");
        Objects.requireNonNull(right, () -> "right operand must not be null");

        final Integer result = Integer.valueOf(operator.calculate(left.intValue(), right.intValue()));
        if (log.isDebugEnabled()) {
            log.debug("calculate(): result = {}", result);
        }

        return result;
    }

    /**
     * Перечисление, в котором определены доступные операции.
     */
    public enum Operation {
        /**
         * Операция сложения двух чисел.
         */
        Add((x, y) -> x + y),
        /**
         * Операция вычитания двух чисел.
         */
        Subtract((x, y) -> x - y),
        /**
         * Операция умножения двух чисел.
         */
        Multiply((x, y) -> x * y),
        /**
         * Операция деления двух чисел.
         */
        Divide((x, y) -> x / y);

        private final IntBinaryOperator operator;

        Operation(IntBinaryOperator operator) {
            this.operator = operator;
        }

        /**
         * Выполнить операцию, которую представляет данный экземпляр, и вернуть результат.
         *
         * @param left  левый операнд операции
         * @param right правый операнд операции
         * @return результат вычисления
         */
        public int calculate(int left, int right) {
            return operator.applyAsInt(left, right);
        }
    }
}
