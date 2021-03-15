package ru.yakubov.vote;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.slf4j.LoggerFactory.getLogger;


@ContextConfiguration({"classpath:spring/inmemory.xml"})
@ExtendWith(SpringExtension.class)
@ExtendWith(TimingExtention.class)
public abstract class AbstractInmemoryTest {
    private static final Logger log = getLogger("result");
    private static final StringBuilder results = new StringBuilder();



}
