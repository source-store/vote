package ru.yakubov.vote;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtention.class)
public abstract class AbstractTest {
    private static final Logger log = getLogger("result");
    private static final StringBuilder results = new StringBuilder();



}
