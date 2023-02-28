package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith( MockitoExtension.class )
public class StocksPortfolioTest {
    @Mock
    private IStockMarketService stockMarket;

    @InjectMocks
    private StocksPortfolio portfolio;

    @Test
    public void getTotalValue() {
        portfolio.addStock(new Stock("APPLE", 1));
        portfolio.addStock(new Stock("IBM", 2));
        portfolio.addStock(new Stock("TSLA", 5));

        Mockito.when(stockMarket.lookUpPrice("APPLE")).thenReturn(150.0);
        Mockito.when(stockMarket.lookUpPrice("IBM")).thenReturn(130.0);
        Mockito.when(stockMarket.lookUpPrice("TSLA")).thenReturn(200.0);

        double result = portfolio.getTotalValue();

        assertEquals(1500.0, result, 0.0);
        Mockito.verify(stockMarket, times(3)).lookUpPrice(anyString());
    }
}
