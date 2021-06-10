package by.imix.cms.controllers;

import by.imix.arithmetic.controllers.ArithmeticController;
import by.imix.arithmetic.controllers.ArithmeticResponse;
import by.imix.arithmetic.services.ArithmeticService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.NestedServletException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Обработчик исключений, которые выбрасывают {@link ArithmeticController} и {@link ArithmeticService}.
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {
    /**
     * {@value org.springframework.http.HttpHeaders#CONTENT_TYPE} для ответов с ошибками:
     * {@value MediaType#TEXT_PLAIN_VALUE} в кодировке {@code "UTF-8"}.
     */
    private static MediaType messageType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);

    private static ResponseEntity<ArithmeticResponse<Void>> handleClientException(Exception exception, HttpStatus status) {
        Objects.requireNonNull(exception, () -> "exception must not be null");

        final Throwable cause = ExceptionUtils.getRootCause(exception);
        if (log.isDebugEnabled()) {
            log.debug("handleClientException(): exception = {}", ExceptionUtils.getMessage(cause));
        }

        return ResponseEntity.status(status).contentType(messageType).body(ArithmeticResponse.failureOf(cause));
    }

    private static ResponseEntity<ArithmeticResponse<Void>> handleServerException(Exception exception, HttpStatus status) {
        Objects.requireNonNull(exception, () -> "exception must not be null");

        final String message = ExceptionUtils.getThrowableList(exception).stream() //
                .map(ExceptionUtils::getMessage).collect(Collectors.joining("caused by:\n"));
        log.error(message, exception);

        return ResponseEntity.status(status).contentType(messageType).body(ArithmeticResponse.failureOf(exception));
    }

    /**
     * Обработчик ошибок арифметики, таких как деление не ноль.
     *
     * @param exception исходное исключение
     * @return ответ с кодом {@code 406} ("Not Acceptable") и телом с информацией об исключении
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ArithmeticResponse<Void>> handleClientException(ArithmeticException exception) {
        return handleClientException(exception, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Обработчик ошибок ввода пользователя, таких как название операции или некорректные значения операндов.
     *
     * @param exception исходное исключение: {@link NullPointerException}, {@link IllegalArgumentException},
     *                  {@link TypeMismatchException} или {@link NestedServletException}
     * @return ответ с кодом {@code 400} ("Bad Request") и телом с информацией об исключении
     */
    @ExceptionHandler({ //
            NullPointerException.class, //
            IllegalArgumentException.class, //
            TypeMismatchException.class, //
            NestedServletException.class //
    })
    public ResponseEntity<ArithmeticResponse<Void>> handleClientException(Exception exception) {
        return handleClientException(exception, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик ошибок, которые мы не ждали.
     *
     * @param exception любое исключение, которое не попадает по параметры выше
     * @return ответ с кодом {@code 500} ("Internal Server Error") и телом с информацией об исключении и его причинах
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ArithmeticResponse<Void>> handleServerException(Exception exception) {
        return handleServerException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
