package by.imix.arithmetic.controllers;

import by.imix.arithmetic.services.ArithmeticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Пример REST-контроллера, через который можно выполнять арифметические вычисления.
 *
 * @see ArithmeticService
 */
@RestController
@RequestMapping(value = "/arithmetic", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Вычисление арифметических операций")
@RequiredArgsConstructor
public class ArithmeticController {
    @Autowired
    private final ArithmeticService service;

    /**
     * Вызывает {@link ArithmeticService#getOperations()}.
     *
     * @return список доступных операций
     * @see ArithmeticService#getOperations()
     */
    @GetMapping
    @ApiOperation("Получить список доступных операций")
    public ArithmeticResponse<Collection<ArithmeticService.Operation>> getOperations() {
        return ArithmeticResponse.successOf(service.getOperations());
    }

    /**
     * Вызывает {@link ArithmeticService#calculate(ArithmeticService.Operation, Integer, Integer)}.
     *
     * @param operator операция, которую необходимо посчитать
     * @param left     левый операнд операции
     * @param right    правый операнд операции
     * @return результат вычисления
     * @see ArithmeticService#calculate(ArithmeticService.Operation, Integer, Integer)
     */
    @PostMapping
    @ApiOperation("Вычислить результат операции над переданными значениями")
    public ArithmeticResponse<Integer> calculate( //
                                                  @RequestParam("operator") ArithmeticService.Operation operator, //
                                                  @RequestParam("left") Integer left, //
                                                  @RequestParam("right") Integer right) {

        return ArithmeticResponse.successOf(service.calculate(operator, left, right));
    }
}
