package guigamelogic;

import stock.Stock;
import storage.StockType;
import ui.GlobalMethodsAndAttributes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
       GlobalMethodsAndAttributes.playerStockMap = player.getStocks();
       GlobalMethodsAndAttributes.playerStocks = player.getStockNames();

       if(GlobalMethodsAndAttributes.playerStockMap.isEmpty()){

       } else {
           ArrayList<String> playerStocksLists = new ArrayList<String>(GlobalMethodsAndAttributes.playerStockMap.keySet());
           GlobalMethodsAndAttributes.showHoldings(playerStocksLists);

           while (!GlobalMethodsAndAttributes.playerStockMap.containsKey(playerStock.getSymbol())){

               return;
           }
           boolean menuOpen = true;

           while (menuOpen){

               if(GlobalMethodsAndAttributes.playerStockMap.get(playerStock.getSymbol()) >= numberOfStockSold){
                   player.getAccount().calculateBalance(numberOfStockSold *
                           inventory.findBySymbol(playerStock.getSymbol()).getCurrentPrice());

                   GlobalMethodsAndAttributes.playerStockMap.put(playerStock.getSymbol(), GlobalMethodsAndAttributes.playerStockMap.get(playerStock.getSymbol()) - numberOfStockSold);

                   if(GlobalMethodsAndAttributes.playerStockMap.get(playerStock.getSymbol()) == 0){
                       GlobalMethodsAndAttributes.playerStockMap.remove(playerStock.getSymbol());
                   }
                   menuOpen = false;
               }
               else {
                   showNotEnoughStockMessage(text);
               }

           }

       }

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
        jTextArea.append("***UnAuthorized sale! Not Enough Stock!***\n");
    }

    private static boolean isValidStockSymbol(String stockSymbol){
        return inventory.findBySymbol(stockSymbol) != null;
    }


//    public static JTextArea showPlayerHoldings(JTextArea sellMenuTextArea) {
//
//        sellMenuTextArea.append(String.format("%-15s%-20s\n","", "         YOUR HOLDINGS      "));
//        sellMenuTextArea.append(String.format("%-15s%-20s%-15s\n","", "Stock Symbol", "Quantity"));
//
//        for (String playerStock : playerStocks) {
//            sellMenuTextArea.append(String.format("%-15s%-20s%15s\n","", playerStock,
//                    playerStockMap.get(playerStock)));
//        }
//        return sellMenuTextArea;
//    }


    public static JTable showPlayerHoldings(){
        String[] cols = {"Stock Name", "Quantity"};
        DefaultTableModel tableModel = new DefaultTableModel(cols,0);
        JTable stocks = new JTable(tableModel);
        List<String> stocksList = playerStocks;

        for(int i = 0; i < stocksList.size(); i++){
            String symbol = stocksList.get(i);
            int quantity = playerStockMap.get(symbol);

            Object[] data = {symbol, quantity};
            tableModel.addRow(data);
        }

        return stocks;
    }


}
