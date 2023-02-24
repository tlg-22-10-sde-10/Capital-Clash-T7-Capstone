package marketreturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketReturnGeneratorTest {
    private MarketReturnGenerator mkt;

    @BeforeEach
    void setUp() {
         mkt = new MarketReturnGenerator();
    }

    @Test
    void nextMarketReturn() {
        double upperbound = 0.02;
        double lowerbound = 0.01;

        double mktReturn = mkt.nextMarketReturn(1);
        assertTrue(mktReturn >= lowerbound);
        assertTrue(mktReturn <= upperbound);

    }
}