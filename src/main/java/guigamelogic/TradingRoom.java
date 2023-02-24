package guigamelogic;

import game.GuiLogic;
import players.Computer;
import players.Player;
import stock.Stock;
import storage.StockInventory;
import storage.StockType;
import ui.GlobalMethodsAndAttributes;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ui.GlobalMethodsAndAttributes.*;

public class TradingRoom {


    public static void menuOneBuy(int day, String stockSymbol, int numberOfStockPurchased, JTextArea textArea) throws UnsupportedAudioFileException, LineUnavailableException, IOException {


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

        buyMenuTextArea.append(String.format("%-10s %-20s %-15s %-18s %-11s","",
                "Stock Name", "Symbol", "Current Price", "Sector"));

        for (Stock stock : inventory.getAllStocks()) {
            buyMenuTextArea.append("\n" + stock.toString());
        }

        return buyMenuTextArea;
    }

    public static JTextArea playerReport (int day, Player player, StockInventory inventory, JTextArea stockHoldingsTextArea) {
        if (player != null && inventory != null) {

            double playerStockBalance = player.getBalanceFromHolding(inventory);
            Object playerStock = player.getStocks() == null ? "Empty" : player.getStocks();

            String playerBalance = "  YOU\n" + "  Stocks: " + playerStock + "\n  Cash Balance: $" + String.format("%.02f", player.getAccount().getCashBalance()) + "\n" + "  Stock Balance: $" +
                    String.format("%.02f", playerStockBalance) + "\n" + "  Net Balance: $" + String.format("%.02f", playerStockBalance + player.getAccount().getCashBalance());

            stockHoldingsTextArea.append(playerBalance);


        }
        return stockHoldingsTextArea;
    }

    public static JTextArea brotherReport (int day, Computer brother, StockInventory inventory, JTextArea stockHoldingsTextArea) {
        if (brother != null && inventory != null) {

            double brotherStockBalance = brother.getBalanceFromHolding(inventory);
            Object brotherStock = brother.getStocks() == null ? "Empty" : brother.getStocks();

            String brotherBalance = "  BROTHER\n" + "  Stocks: " + brotherStock + "\n  Cash Balance: $" + String.format("%.02f", brother.getAccount().getCashBalance()) + "\n" + "  Stock Balance: $" +
                    String.format("%.02f", brotherStockBalance) + "\n" + "  Net Balance: $" + String.format("%.02f", brotherStockBalance + brother.getAccount().getCashBalance());

            stockHoldingsTextArea.append(brotherBalance);
        }
        return stockHoldingsTextArea;
    }

    public JTable showStocks(){
        String[] cols = {"Stock Name", "Symbol", "Current Price", "Sector"};
        DefaultTableModel tableModel = new DefaultTableModel(cols,0);
        List<Stock> stocksList = inventory.getAllStocks();
        for(int i = 0; i < stocksList.size(); i++){
            String stockName = stocksList.get(i).getStockName();
            String symbol = stocksList.get(i).getSymbol();
            double curPrice = stocksList.get(i).getCurrentPrice();
            StockType stockType = stocksList.get(i).getSector();

            Object[] data = {stockName,symbol,curPrice,stockType};
            tableModel.addRow(data);
        }

        return new JTable(tableModel);
    }



}