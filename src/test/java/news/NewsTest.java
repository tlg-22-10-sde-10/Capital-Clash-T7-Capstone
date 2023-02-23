package news;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewsTest {

    private News news;

    @BeforeEach
    void setUp() {

        news = new News();

    }

    @Test
    void getNewsContent() {
        String newsContent = "Justice Dept. Sues to Block $13 Billion Deal by UnitedHealth Group!";
        assertEquals(newsContent,news.getNewsContent(8));
    }

    @Test
    void getNewsContent_FailDefault(){
        String failMessage = "News not found";
        assertEquals(failMessage, news.getNewsContent(14));
    }
}