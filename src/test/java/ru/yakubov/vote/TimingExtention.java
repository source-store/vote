package ru.yakubov.vote;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class TimingExtention implements BeforeTestExecutionCallback, AfterTestExecutionCallback, BeforeAllCallback, AfterAllCallback {

    public static final Logger log = LoggerFactory.getLogger("result");

    private StopWatch stopWatch;

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        log.info('\n' + stopWatch.prettyPrint() + '\n');
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        stopWatch.stop();
        log.info("Stop stopWatch");
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        stopWatch = new StopWatch("Execution time of " + extensionContext.getRequiredTestClass().getSimpleName());

    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        log.info("Start stopWatch");
        stopWatch.start(extensionContext.getDisplayName());
    }
}
