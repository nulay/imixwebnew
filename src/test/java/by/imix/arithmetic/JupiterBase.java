package by.imix.arithmetic;

import org.jboss.logging.MDC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * Базовый класс для тестов, содержит общие определения и логирует начало и конец тестов.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class JupiterBase {
    private static final String MDC_ID = "testName";

    private static void logInfo(TestInfo info, Consumer<Logger> logFun) {
        info.getTestClass().map(LoggerFactory::getLogger).ifPresent(logFun);
    }

    @BeforeEach
    void startUp(TestInfo info) {
        MDC.put(MDC_ID, info.getTestMethod().map(Method::getName).orElseGet(() -> ""));
        logInfo(info, log -> log.info("НАЧАЛО: \"{}\"", info.getDisplayName()));
    }

    @AfterEach
    void shutdown(TestInfo info) {
        logInfo(info, log -> log.info("КОНЕЦ:  \"{}\"", info.getDisplayName()));
        MDC.remove(MDC_ID);
    }
}
