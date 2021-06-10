package by.imix.arithmetic.controllers;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@ApiModel(description = "Ответное сообщение контроллера")
public class ArithmeticResponse<T> {
    @ApiModelProperty("Время создания ответного сообщения")
    LocalDateTime timestamp;
    @ApiModelProperty("Цепочка исключениий, если произшлоа ошибка")
    List<String> exceptions;
    @ApiModelProperty("Тело ответного сообщения с результатом обработки запроса")
    T value;

    public static <T> ArithmeticResponse<T> successOf(T body) {
        final ArithmeticResponse<T> output = new ArithmeticResponse<>();
        output.setTimestamp(LocalDateTime.now());
        output.setValue(body);
        return output;
    }

    public static ArithmeticResponse<Void> failureOf(Throwable exception) {
        Objects.requireNonNull(exception, () -> "exception must not be null");

        final ArithmeticResponse<Void> output = new ArithmeticResponse<>();
        output.setTimestamp(LocalDateTime.now());
        output.setExceptions(ExceptionUtils.getThrowableList(exception).stream() //
                .map(ExceptionUtils::getMessage).collect(Collectors.toList()));
        return output;
    }
}
