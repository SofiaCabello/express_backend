package org.example.express_backend;

import org.example.express_backend.service.LogisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LogisticServiceTest {

    @Autowired
    private LogisticService logisticService;

    @Test
    public void testGetTransferRoute(){
        Long originId = 210522L;
        Long destinationId = 150721L;

        logisticService.getTransferRoute(originId, destinationId);
    }
}
