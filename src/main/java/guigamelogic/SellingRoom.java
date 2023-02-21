package guigamelogic;

import stock.Stock;
import ui.GlobalMethodsAndAttributes;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static ui.GlobalMethodsAndAttributes.*;

public class SellingRoom {

    public static void menuTwoSell(int day, String stockSymbol, int numberOfStockSold, JTextArea text) throws FileNotFoundException {
        if(!isValidStockSymbol(stockSymbol)){

        }

        Stock playerStock = inventory.findBySymbol(stockSymbol);
        if(!hasSufficientStock(playerStock,numberOfStockSold)){
            showNotEnoughStockMessage(text);
        }
        else {
            sellStock(day, playerStock, numberOfStockSold, text);
        }

    }

    private static void sellStock(int day, Stock playerStock, int numberOfStockSold, JTextArea text){
        playerStockMap.put(playerStock.getSymbol(), playerStockMap.get(playerStock.getSymbol())- numberOfStockSold);

        if(playerStockMap.get(playerStock.getSymbol()) == 0){
            playerStocks.remove(playerStock.getSymbol());
        }

        player.setStockNames(playerStocks);
        player.setStocks(playerStockMap);
        player.getAccount().calculateBalance(numberOfStockSold * playerStock.getCurrentPrice());
        showSuccessfulSaleMessage(numberOfStockSold,playerStock,text);
    }

    private static boolean hasSufficientStock(Stock playerStock, int numberOfStockSold){
        return playerStockMap.containsKey(playerStock.getSymbol()) && playerStockMap.get(playerStock.getSymbol()) >= numberOfStockSold;
    }


    private static void showSuccessfulSaleMessage(int numberOfStockSold, Stock playerStock, JTextArea text){
        text.append("***Successfully Sold " + numberOfStockSold +
                " share of " + playerStock.getStockName() + " ***\n");
    }

    private static void showNotEnoughStockMessage(JTextArea jTextArea){
        jTextArea.append("***UnAuthiroized sale! Not Enough Stock!***\n");
    }

    private static boolean isValidStockSymbol(String stockSymbol){
        return inventory.findBySymbol(stockSymbol) != null;
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
