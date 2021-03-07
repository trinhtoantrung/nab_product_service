package com.nab.assignment.product.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuditorAwareImplTest {

    @InjectMocks
    private AuditorAwareImpl auditorAware;

    @Test
    void getCurrentAuditor() {
        assertEquals(Optional.of("system"), auditorAware.getCurrentAuditor());
    }
}