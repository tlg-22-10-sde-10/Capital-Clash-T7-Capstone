package guigamelogic;

import players.Computer;
import players.Player;
import stock.Stock;
import storage.StockInventory;
import ui.GlobalMethodsAndAttributes;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.GlobalMethodsAndAttributes.*;

public class TradingRoom {


    public static void menuOneBuy(int day, String stockSymbol, int numberOfStockPurchased, JTextArea textArea) throws UnsupportedAudioFileException, LineUnavailableException, IOException {


        GlobalMethodsAndAttributes.initializeGlobalInstances();

        if (!isValidStockSymbol(stockSymbol)) {
            showInvalidStockSymbolMessage(day, textArea);
            stockSymbol = promptForValidStockSymbol(day, textArea, stockSymbol);
        }
        Stock playerStock = inventory.findBySymbol(stockSymbol);
        double valueOfStockPurchasedByPlayer = numberOfStockPurchased * playerStock.getCurrentPrice();
        if (valueOfStockPurchasedByPlayer > player.getAccount().getCashBalance()) {
            showNotEnoughBalanceMessage(textArea);
        } else {
            purchaseStock(day, playerStock, numberOfStockPurchased, textArea);
        }
        purchaseStockForBrother();
    }

    private static void purchaseStockForBrother() {
        int numberOfSharePurchased = 1 + (int) (Math.random() * 6);
        Stock brotherStock = inventory.getRandomStock();

        if (brotherStockMap.containsKey(brotherStock.getSymbol())) {
            brotherStockMap.put(brotherStock.getSymbol(), numberOfSharePurchased + brotherStockMap.get(brotherStock.getSymbol()));
        } else {
            brotherStockMap.put(brotherStock.getSymbol(), numberOfSharePurchased);
        }

        brother.setStocks(brotherStockMap);
        brother.getAccount().deductBalance(numberOfSharePurchased * brotherStock.getCurrentPrice());
    }

    private static boolean isValidStockSymbol(String stockSymbol) {
        return inventory.findBySymbol(stockSymbol) != null;
    }

    private static void showInvalidStockSymbolMessage(int day, JTextArea textArea) {
        textArea.append("***Stock not offered. Please select from the list below***\n");
        showTradingRoomDashboard(day, textArea);
    }

    public static String promptForValidStockSymbol(int day, JTextArea textArea, String stocksymbol) {
        textArea.append("\nPlease Enter the symbol of the stock you want to purchase: \n");
        return stocksymbol;
    }

    private static void showNotEnoughBalanceMessage(JTextArea textArea) {
        textArea.append("***Unauthorized Purchased! Not enough balance!***\n");
    }

    private static void purchaseStock(int day, Stock playerStock, int numberOfStockPurchased, JTextArea textArea) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (playerStockMap.containsKey(playerStock.getSymbol())) {
            playerStockMap.put(playerStock.getSymbol(), playerStockMap.get(playerStock.getSymbol()) + numberOfStockPurchased);
        } else {
            playerStockMap.put(playerStock.getSymbol(), numberOfStockPurchased);
        }

        playerStocks.add(playerStock.getSymbol());
        player.setStockNames(playerStocks);
        player.setStocks(playerStockMap);
        player.getAccount().deductBalance(numberOfStockPurchased * playerStock.getCurrentPrice());

        showSuccessfulPurchaseMessage(numberOfStockPurchased, playerStock, textArea);
    }

    private static void showSuccessfulPurchaseMessage(int numberOfStockPurchased, Stock playerStock, JTextArea textArea) {
        textArea.append("***Successfully Purchased " + numberOfStockPurchased +
                " shares of " + playerStock.getStockName() + " ***\n");
    }


    private static void showTradingRoomDashboard(int day, JTextArea textArea) {
        textArea.append("Trading Room Stock Dashboard - Day " + day + "\n");
        for (Stock stock : inventory.getAllStocks()) {
            textArea.append(stock.toString() + "\n");
        }

    }


    public static JTextArea showStockInventory(JTextArea buyMenuTextArea) {

        for (Stock stock : inventory.getAllStocks()) {
            buyMenuTextArea.append(stock.toString() + "\n");
        }
        return buyMenuTextArea;
    }

    public static JTextArea playerVsBrotherReports(int day, Player player, Computer brother, StockInventory inventory, JTextArea stockHoldingsTextArea) {
        //if (player != null && brother != null && inventory != null) {

            double playerStockBalance = player.getBalanceFromHolding(inventory);
            double brotherStockBalance = brother.getBalanceFromHolding(inventory);


//            System.out.println(String.format(ANSI_YELLOW + "%-42s DAY: %-10s\n", "", day + ANSI_RESET));
//            System.out.println(String.format("%-18s %-42s %-14s", "", ANSI_CYAN_BACKGROUND+"You         " + ANSI_RESET, ANSI_CYAN_BACKGROUND + "Brother     " + ANSI_RESET));
//
//            System.out.println(String.format("%-18s Stocks: %-25s Stocks: %-10s", "",
//                    player.getStocks() == null ? "Empty" : player.getStocks(),
//                    brother.getStocks() == null ? "Empty" : brother.getStocks()));
//
//            System.out.println(String.format("%-18s Cash Balance:$%-19.2f Cash Balance:$%-10.2f",
//                    "", player.getAccount().getCashBalance(), brother.getAccount().getCashBalance()));
//            System.out.println(String.format("%-18s Stock Balance:$%-18.2f Stock Balance:$%-10.2f",
//                    "", playerStockBalance, brotherStockBalance));
//            System.out.println(String.format("%-18s Net Balance:$%-20.2f Net Balance:$%-10.2f\n",
//                    "", playerStockBalance + player.getAccount().getCashBalance(), brotherStockBalance + brother.getAccount().getCashBalance()));
//
            stockHoldingsTextArea.append(String.valueOf((playerStockBalance + brotherStockBalance)));


        return stockHoldingsTextArea;

    }


}