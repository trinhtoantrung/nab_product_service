package com.nab.assignment.product.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JpaAuditConfigTest {

    @InjectMocks
    private JpaAuditConfig jpaAuditConfig;

    @Test
    void auditorAware() {
        assertTrue(AuditorAwareImpl.class.isInstance(jpaAuditConfig.auditorAware()));
    }
}