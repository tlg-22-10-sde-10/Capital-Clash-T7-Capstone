package stock;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.StockType;

public class StockTest {

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock("Apple","APPL",100,1.0,1.0,3.0, StockType.Technology);
    }

    @Test
    void getStockName() {
        String stockName = "Apple";
        assertEquals(stockName, stock.getStockName());
    }

    @Test
    void setStockName() {
        String newName = "JP Morgan";
        stock.setStockName("JP Morgan");
        assertEquals(newName, stock.getStockName());
    }

    @Test
    void getSymbol() {
        String stockSymbol = "APPL";
        assertEquals(stockSymbol, stock.getSymbol());
    }

    @Test
    void setSymbol() {
        String newSymbol = "JPM";
        stock.setSymbol("JPM");
        assertEquals(newSymbol, stock.getSymbol());
    }

    @Test
    void getCurrentPrice() {
        double currentPrice = 100;
        Assertions.assertEquals(currentPrice, stock.getCurrentPrice());
    }

    @Test
    void setCurrentPrice() {
        double newPrice = 110;
        stock.setCurrentPrice(110);
        Assertions.assertEquals(newPrice, stock.getCurrentPrice());
    }

    @Test
    void getBeta(){
        double currentBeta = 1.0;
        Assertions.assertEquals(currentBeta,stock.getBeta());
    }

    @Test
    void setBeta() {
        double newBeta = 2.0;
        stock.setBeta(2.0);
        Assertions.assertEquals(newBeta,stock.getBeta());
    }

    @Test
    void getAlpha() {
        double currentAlpha = 1.0;
        Assertions.assertEquals(currentAlpha, stock.getAlpha());
    }

    @Test
    void setAlpha() {
         double newAlpha = 2.0;
         stock.setAlpha(2.0);
         Assertions.assertEquals(newAlpha, stock.getAlpha());
    }

    @Test
    void getResidual() {
        double currentResudual = 3.0;
        Assertions.assertEquals(currentResudual, stock.getResidual());
    }

    @Test
    void setResidual() {
        double newRsidual = 4.0;
        stock.setResidual(4.0);
        Assertions.assertEquals(newRsidual, stock.getResidual());
    }

    @Test
    void getSector() {
        StockType stockType = StockType.Technology;
        assertEquals(stockType, stock.getSector());
    }

    @Test
    void setSector() {
        StockType newStockType = StockType.Communication_Services;
        stock.setSector(StockType.Communication_Services);
        assertEquals(newStockType, stock.getSector());
    }

    @Test
    void updateStockPriceForTheDay() {
        double currentPrice = 100;
        double newPrice = stock.UpdateStockPriceForTheDay(stock.getCurrentPrice(),2.0,9);
        assertNotEquals(currentPrice, newPrice);
    }
}
