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
                    , "Error", JOptionPane.ERROR_MESSAGE);
        } else {
                ArrayList<String> playerStockList = new ArrayList<>(playerStockMap.keySet());
                showHoldings(playerStockList);

                if (!playerStockMap.containsKey(stockSymbol)) {
                    JOptionPane.showMessageDialog(null, "**This Stock is not in your holdings***\nPlease try again.***"
                            , "Error", JOptionPane.ERROR_MESSAGE);
                    showHoldings(playerStockList);
                    return;
                }

                if (!isPositiveInteger(quantityInput)) {
                    JOptionPane.showMessageDialog(null, "***Your input is not an integer. Fractional Shares not allowed.\nPlease try again.***"
                            , "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity = Integer.parseInt(quantityInput);
                int currentQuantity = playerStockMap.get(stockSymbol);
                if ( currentQuantity >= quantity) {
                    double pricePerShare = inventory.findBySymbol(stockSymbol).getCurrentPrice();
                    double totalSalesPrice = quantity * pricePerShare;
                    player.getAccount().calculateBalance(totalSalesPrice);

                    int newQuantity  = currentQuantity - quantity;
                    if (newQuantity == 0) {
                        playerStockMap.remove(stockSymbol);
                    }
                    else {
                        playerStockMap.put(stockSymbol,newQuantity);
                    }

                    JOptionPane.showMessageDialog(null, "***Successfully Sold " + quantityInput + " shares of " +
                            inventory.findBySymbol(stockSymbol).getStockName() + "***", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "***Please Try again and enter a valid stock quantity.***", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
        }

    }

    public static JTextArea showPlayerHoldings(JTextArea sellMenuTextArea) {

        String sellPageTitle = "       YOUR HOLDINGS\n      ";
        String sellOptionTitles = "\nStock Symbol:       " + "Quantity:\n";

        sellMenuTextArea.append(sellPageTitle);
        sellMenuTextArea.append(sellOptionTitles);

        for (String playerStock : playerStocks) {
            sellMenuTextArea.append(playerStock + "                   " +
                    playerStockMap.get(playerStock));
        }
        return sellMenuTextArea;
    }


}
