package guigamelogic;

import ui.GlobalMethodsAndAttributes;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static ui.GlobalMethodsAndAttributes.*;

public class SellingRoom {

    public static void menuTwoSell(String stockSymbol, String quantityInput) throws FileNotFoundException {

        GlobalMethodsAndAttributes.initializeGlobalInstances();

        if (playerStockMap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "***No Current Holdings. Transaction cannot be completed***"
                    , "Error", JOptionPane.ERROR);
        } else {
            ArrayList<String> playerStockList = new ArrayList<>(playerStockMap.keySet());
            showHoldings(playerStockList);

            if (!playerStockMap.containsKey(stockSymbol)) {
                JOptionPane.showMessageDialog(null, "**This Stock not you holdings***\n***Please try again"
                        , "Error", JOptionPane.ERROR_MESSAGE);
                showHoldings(playerStockList);
                return;
            }

            if (!isPositiveInteger(quantityInput)) {
                JOptionPane.showMessageDialog(null, "***Your input is not an integer. Please try again.***\nFractional Shares not allowed"
                        , "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity = Integer.parseInt(quantityInput);

            if (playerStockMap.get(stockSymbol) >= quantity) {
                player.getAccount().calculateBalance(quantity * inventory.findBySymbol(stockSymbol).getCurrentPrice());

                playerStockMap.put(stockSymbol, playerStockMap.get(stockSymbol) - quantity);

                if (playerStockMap.get(stockSymbol) == 0) {
                    playerStockMap.remove(stockSymbol);
                }

                JOptionPane.showMessageDialog(null, "***Sucessfully Sold " + quantityInput + " shares of " +
                        inventory.findBySymbol(stockSymbol).getStockName() + "***", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "***Please Try again and enter a valid stock quantity.***", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }


        }


    }


}
