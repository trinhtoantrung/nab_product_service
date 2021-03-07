package com.nab.assignment.product.config.async;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ApplicationAsyncConfigTest {

    @InjectMocks
    private ApplicationAsyncConfig applicationAsyncConfig;

    @Test
    void getAsyncExecutor() {
        Executor executor = applicationAsyncConfig.getAsyncExecutor();
        assertTrue(ThreadPoolTaskExecutor.class.isInstance(executor));
    }
}