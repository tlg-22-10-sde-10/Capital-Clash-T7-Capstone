package storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stock.Stock;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class StockInventoryTest {
    private StockInventory inventory;
    @BeforeEach
    void setUp() throws FileNotFoundException {
        inventory = new StockInventory();
    }

    @Test
    void getAllStocks() {
        assertTrue(inventory.getAllStocks().size() >= 0);
        assertEquals("Apple", inventory.getAllStocks().get(0).getStockName());
    }

    @Test
    void findBySymbol() {

        String stockName = inventory.findBySymbol("AAPL").getStockName();
        assertEquals("Apple",stockName);
    }


    @Test
    void getRandomStock() {
        assertTrue(inventory.getRandomStock() instanceof Stock);
    }
}