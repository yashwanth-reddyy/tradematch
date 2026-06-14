package com.tradematch;

import com.tradematch.model.Order;
import com.tradematch.repository.TradeRepository;
import com.tradematch.service.OrderMatchingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrderMatchingServiceTest {

    @Test
    void testOrderMatchingAndPersistencePipeline() {
        TradeRepository mockRepo = Mockito.mock(TradeRepository.class);
        ApplicationEventPublisher mockPublisher = Mockito.mock(ApplicationEventPublisher.class);
        OrderMatchingService engine = new OrderMatchingService(mockRepo, mockPublisher);

        Order sellOrder = new Order(UUID.randomUUID().toString(), "AAPL", "SELL", 150.00, 5);
        engine.processOrder(sellOrder);

        Order buyOrder = new Order(UUID.randomUUID().toString(), "AAPL", "BUY", 150.00, 5);
        engine.processOrder(buyOrder);

        verify(mockRepo, times(1)).save(any());
        verify(mockPublisher, times(1)).publishEvent(any());
    }
}