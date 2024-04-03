package org.example.express_backend;

import org.example.express_backend.util.EmailUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailUtilTest {
    @Test
    public void testSendCode() {
        EmailUtil emailUtil = new EmailUtil();
        assertTrue(emailUtil.sendCode("sofiacabello666@outlook.com"));
    }
}
