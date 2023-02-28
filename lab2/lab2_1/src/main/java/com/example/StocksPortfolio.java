package com.example;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockMarketService stockMarket;

    public StocksPortfolio(IStockMarketService stockMarket) {
        this.stocks= new ArrayList<Stock>();
        this.stockMarket = stockMarket;
    }


    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0.0;
        for (Stock s : stocks) {
            total += s.getQuantity() * stockMarket.lookUpPrice(s.getlabel());
        }
        return total;
    }
}
