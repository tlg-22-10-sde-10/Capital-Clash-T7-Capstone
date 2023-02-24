package random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomNumberForNewsTest {

    private RandomNumberForNews randomGen;

    @BeforeEach
    void setUp() {
        randomGen = new RandomNumberForNews();
    }

//    @Test
//    void getRandomNumber() {
//        int range = 10;
//        int randomNum = randomGen.getRandomNumber();
//
//        assertTrue(range <= randomNum);
//
//    }
}