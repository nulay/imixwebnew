package by.imix.arithmetic.controllers;

import by.imix.arithmetic.JupiterBase;
import by.imix.arithmetic.services.ArithmeticService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("PrototypeServiceController")
class TestArithmeticController extends JupiterBase {
    private static final String MAPPING = "/arithmetic";
    @Autowired
    TestRestTemplate testRestTemplate;

    static Stream<Arguments> testCalculate() {
        return Stream.of( //
                Arguments.of(ArithmeticService.Operation.Add, 8, 7, 15), //
                Arguments.of(ArithmeticService.Operation.Subtract, 8, 7, 1), //
                Arguments.of(ArithmeticService.Operation.Multiply, 8, 7, 56), //
                Arguments.of(ArithmeticService.Operation.Divide, 8, 7, 1) //
        );
    }

    static Stream<Arguments> testExceptions() {
        return Stream.of( //
                Arguments.of(null, null, null, HttpStatus.BAD_REQUEST, "'operator' is not present"), //
                Arguments.of("Add", null, null, HttpStatus.BAD_REQUEST, "'left' is not present"), //
                Arguments.of("Add", "", "", HttpStatus.BAD_REQUEST, "'left' is not present"), //
                Arguments.of("Add", "0", null, HttpStatus.BAD_REQUEST, "'right' is not present"), //
                Arguments.of("Add", "0", "", HttpStatus.BAD_REQUEST, "'right' is not present"), //
                Arguments.of("Add", "aaa", "0", HttpStatus.BAD_REQUEST, "input string: \"aaa\""), //
                Arguments.of("a", "aaa", null, HttpStatus.BAD_REQUEST, "No enum constant"), //
                Arguments.of("Divide", "1", "0", HttpStatus.NOT_ACCEPTABLE, "/ by zero") //
        );
    }

    @Test
    @DisplayName("<GET> " + MAPPING)
    void testGetOperations() {
        try {
            final ResponseEntity<GetResponse> response = testRestTemplate.getForEntity(MAPPING, GetResponse.class);
            assertThat(response).isNotNull();

            final GetResponse body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.getExceptions()).isNull();
            assertThat(body.getValue()) //
                    .isNotNull() //
                    .isNotEmpty() //
                    .containsAll(Stream.of(ArithmeticService.Operation.values()).map(Enum::name).collect(Collectors.toList()));

        } catch (final RestClientException e) {
            log.error("", e);
            throw e;
        }
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("<POST> " + MAPPING)
    void testCalculate(ArithmeticService.Operation operator, Integer left, Integer right, Integer expectedResult) {
        final ResponseEntity<PostResponse> response = testRestTemplate.postForEntity( //
                UriComponentsBuilder //
                        .fromUriString(testRestTemplate.getRootUri()) //
                        .path(MAPPING) //
                        .queryParam("operator", operator) //
                        .queryParam("left", left) //
                        .queryParam("right", right) //
                        .build() //
                        .toUri(), //
                null, //
                PostResponse.class);
        assertThat(response).isNotNull();

        final PostResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getExceptions()).isNull();
        assertThat(body.getValue()).isNotNull().isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("<POST> exceptions")
    void testExceptions(String operator, String left, String right, HttpStatus expectedStatus, String expectedMessage) {
        final LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        if (operator != null) {
            parameters.put("operator", List.of(operator));
        }
        if (left != null) {
            parameters.put("left", List.of(left));
        }
        if (right != null) {
            parameters.put("right", List.of(right));
        }

        final ResponseEntity<PostExceptionResponse> response = testRestTemplate.postForEntity( //
                UriComponentsBuilder //
                        .fromUriString(testRestTemplate.getRootUri()) //
                        .path(MAPPING) //
                        .queryParams(parameters) //
                        .build() //
                        .toUri(), //
                null, //
                PostExceptionResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);

        final PostExceptionResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getValue()).isNull();
        assertThat(body.getExceptions()) //
                .isNotNull() //
                .isNotEmpty() //
                .anyMatch(message -> message.contains(expectedMessage));
    }

    static class GetResponse extends ArithmeticResponse<List<String>> {
    }

    static class PostResponse extends ArithmeticResponse<Integer> {
    }

    static class PostExceptionResponse extends ArithmeticResponse<Void> {
    }
}
