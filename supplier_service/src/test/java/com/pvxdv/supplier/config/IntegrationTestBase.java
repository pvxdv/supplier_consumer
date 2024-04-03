package com.pvxdv.supplier.config;

import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ContextConfiguration;

@Getter
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
public class IntegrationTestBase {
        @BeforeAll
    public static void init() {
        Postgres.container.start();
    }

    @AfterAll
    public static void stop() {
        Postgres.container.stop();
    }

}
