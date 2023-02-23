package account;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AccountTest {
    private static Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Player");
    }

    @Test
    void getAccountName() {
        String accountName = "Player";
        assertEquals(accountName,account.getAccountName());
    }

    @Test
    void setAccountName() {
        String newName = "Player2";
        account.setAccountName("Player2");
        assertEquals(newName,account.getAccountName());
    }

    @Test
    void getCashBalance() {
        double balance = 10000;
        Assertions.assertEquals(balance, account.getCashBalance());
    }

    @Test
    void setCashBalance() {
        double newBalance = 1000;
        account.setCashBalance(1000);
        Assertions.assertEquals(newBalance,account.getCashBalance());
    }

    @Test
    void deductBalance() {
        double newBalance = 1000;
        double deduct = 9000;
        account.deductBalance(deduct);
        Assertions.assertEquals(newBalance, account.getCashBalance());


    }

    @Test
    void calculateBalance() {
        double newBalance = 11000;
        double add = 1000;
        account.calculateBalance(add);
        Assertions.assertEquals(newBalance, account.getCashBalance());

    }
}
