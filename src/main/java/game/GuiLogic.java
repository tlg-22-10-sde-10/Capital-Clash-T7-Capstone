package game;

import guigamelogic.CountdownTimer;
import guigamelogic.GameStory;
import guigamelogic.SellingRoom;
import guigamelogic.TradingRoom;
import random.RandomNumberForNews;
import ui.GlobalMethodsAndAttributes;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.GlobalMethodsAndAttributes.*;


public class GuiLogic extends javax.swing.JFrame {

    private static final JFrame frame = new GuiConstructor();

    // Game Buttons
    private JButton newGame;
    private JButton quitGame;
    private JButton continueButton;
    private JButton endTradingDay;
    private JButton buyStock;
    private JButton submitBuyStockMenuButton;
    private JButton cancelBuyStockMenuButton;
    private JButton submitSellStockMenuButton;
    private JButton cancelSellStockMenuButton;
    private JButton sellStock;
    private JButton sleep;
    private JButton playComputer;

    // Game Labels
    private JLabel gameTitle_CAPITAL;
    private JLabel gameTitle_CLASH;
    private JLabel gameTitleText;
    private JLabel backgroundImg;
    private JLabel buyMenuBackgroundImg;
    private JLabel sellMenuBackgroundImg;
    private JLabel breakingNews;
    private JLabel roomBackgroundImg;
    private JLabel timeRemaining;

    private JLabel stockPurchaseHeading;
    private JLabel stockPurchaseQuantityHeading;
    private JLabel stockSellHeading;
    private JLabel stockSellQuantityHeading;

    //Game Panels
    private JPanel welcomeBannerPanel;
    private JPanel tradingTimerPanel;
    private JTextArea newsTicker;
    private JScrollPane newsScrollPane;
    private JPanel buyMenuStocksPanel;
    private JPanel sellMenuStocksPanel;
    private JPanel brotherStockHoldings;
    private JPanel playerStockHoldingsPanel;

    //Game Text & Panes
    private JTextArea gameStoryText;
    private JTextArea playerStockHoldingsTextArea;
    private JTextArea insufficientBuyBalance;
    private JScrollPane scrollPane;
    private JTextArea buyMenuStocksListing;
    private JTextArea sellMenuStocksListing;
    private JTextField stockBuySymbol;
    private JTextField stockBuyQuantity;
    private JTextField stockSellSymbol;
    private JTextField stockSellQuantity;
    private JLabel welcomeBannerPanelLabel;

    // Game ImageIcons
    private ImageIcon tradingRoomBackground;
    private ImageIcon bedroomBackground;
    private ImageIcon buyMenuBackground;
    private ImageIcon sellMenuBackground;
    private ImageIcon img;

    //Game JFrames
    private JFrame buyMenuPopup;
    private JFrame sellMenuPopup;

    public static int getDayCounter() {
        return dayCounter;
    }

    public static void setDayCounter(int dayCounter) {
        GuiLogic.dayCounter = dayCounter;
    }

    //Game Day Counter
    public static int dayCounter = 1;

    public GuiLogic() {
        initGui();
        frame.setVisible(true);
    }



    //Initializing the GUI, new game and quit buttons
    public void initGui() {

        //Unifies the look between different MAC/Windows platforms
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Initializes the Global Variables from the console game
        try {
            GlobalMethodsAndAttributes.initializeGlobalInstances();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        gameTitle_CAPITAL = new JLabel("CAPITAL");
        gameTitle_CLASH = new JLabel("CLASH 2.0");
        gameTitleText = new JLabel("Are you up for the challenge?");
        newGame = new JButton("New Game");
        quitGame = new JButton("Quit Game");

        newGame.setBounds(130, 420, 105, 40);
        newGame.setBackground(Color.GREEN);

        quitGame.setBounds(260, 420, 105, 40);
        quitGame.setBackground(Color.RED);

        gameTitle_CAPITAL.setBounds(100, 20, 300, 50);
        gameTitle_CAPITAL.setFont(new Font("Playfair Display", Font.BOLD, 40));

        gameTitle_CLASH.setBounds(250, 70, 300, 50);
        gameTitle_CLASH.setFont(new Font("Playfair Display", Font.BOLD, 40));
        gameTitle_CLASH.setForeground(Color.RED);

        gameTitleText.setBounds(160, 380, 300, 40);
        gameTitleText.setFont(new Font("Playfair Display", Font.BOLD, 12));

        frame.getContentPane().add(newGame);
        frame.getContentPane().add(quitGame);
        frame.getContentPane().add(gameTitle_CAPITAL);
        frame.getContentPane().add(gameTitle_CLASH);
        frame.getContentPane().add(gameTitleText);

        // New Game Button Event Listener
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                guiGameStory();
            }
        });

        //event listener to quit the game
        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Game intro and story run
                int exitPrompt = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to quit Capital Clash?",
                        "Quit Capital Clash?",
                        JOptionPane.YES_NO_OPTION);
                if (exitPrompt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void guiGameStory() {
        newGame.setVisible(false);
        gameStoryText = new JTextArea(GameStory.displayGameInfo());
        scrollPane = new JScrollPane(gameStoryText);
        continueButton = new JButton("Continue");

        img = new ImageIcon("");
        backgroundImg = new JLabel(img);
        backgroundImg.setBounds(0, 0, 800, 600);

        gameStoryText.setBounds(75, 60, 350, 350);
        gameStoryText.setFont(new Font("Playfair Display", Font.BOLD, 12));
        gameStoryText.setBackground(new Color(0, 0, 0, 65));
        gameStoryText.setForeground(Color.black);
        gameStoryText.setEditable(false);
        gameStoryText.setLineWrap(true);
        gameStoryText.setWrapStyleWord(true);

        scrollPane.getViewport().setOpaque(false);

        continueButton.setBounds(130, 420, 105, 40);
        continueButton.setBackground(Color.GREEN);

        frame.setContentPane(backgroundImg);
        frame.getContentPane().add(gameStoryText);
        frame.getContentPane().add(continueButton);

        // Continue Game Button Event Listener
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                guiTradingRoom();
            }
        });
    }

    public void guiTradingRoom() {

        continueButton.setVisible(false);
        //setting the variables and placements
        welcomeBannerPanel = new JPanel();
        welcomeBannerPanelLabel = new JLabel();
        tradingTimerPanel = new JPanel();
        endTradingDay = new JButton();
        breakingNews = new JLabel();
        //newsTicker = new JPanel();
        buyStock = new JButton();
        sellStock = new JButton();
        brotherStockHoldings = new JPanel();
        playerStockHoldingsPanel = new JPanel();
        playerStockHoldingsTextArea = new JTextArea();
        timeRemaining = new JLabel(CountdownTimer.getTimeRemaining());


        //start trading for the day
        //Executor to call method constantly? For time remaining portion
        CountdownTimer.startTimer(5);

        //changes the background image
        tradingRoomBackground = new ImageIcon("");
        backgroundImg = new JLabel(tradingRoomBackground);

        //setting the background picture and location
        backgroundImg.setBounds(0, 0, 800, 600);

        //setting the text to go into the welcome banner
        welcomeBannerPanelLabel.setText(" Welcome to Trading Day: " + dayCounter);
        welcomeBannerPanelLabel.setForeground(Color.black);
        welcomeBannerPanelLabel.setFont(new Font("Playfair Display", Font.BOLD, 18));
        welcomeBannerPanelLabel.setBounds(290, 10,800,20);

        //setting the welcome banner location and color
        welcomeBannerPanel.setBounds(0, 0, 800, 25);
        welcomeBannerPanel.setBackground(new Color(0, 0, 0, 0));

        //setting the trading timer location and color
        tradingTimerPanel.setBounds(30, 50, 100, 40);
        tradingTimerPanel.setBackground(new Color(0, 0, 0, 65));
        tradingTimerPanel.add(timeRemaining);

        //setting time remaining location
        timeRemaining.setBounds(60,20,100,100);
        timeRemaining.setBackground(Color.GREEN);

        //setting the location end trading day/go to room button
        endTradingDay.setBounds(620, 50, 150, 40);
        endTradingDay.setBackground(Color.GREEN);
        endTradingDay.setText("End Trading Day");

        //setting the location of the breaking news header
        breakingNews.setBounds(330, 100, 300, 50);
        breakingNews.setText("*** BREAKING NEWS ***");

        //setting the location of the news ticker
        int newsIndexOfTheDay = RandomNumberForNews.getRandomNumber();
        newsTicker = new JTextArea(" - " + news.getNewsContent(newsIndexOfTheDay));
        newsScrollPane = new JScrollPane(newsTicker);
        newsScrollPane.getViewport().setOpaque(false);
        newsTicker.setBounds(100, 140, 600, 40);
        newsTicker.setFont(new Font("Playfair Display", Font.BOLD, 12));
        newsTicker.setBackground(new Color(0, 0, 0, 65));
        newsTicker.setForeground(Color.blue);
        newsTicker.setEditable(false);
        newsTicker.setLineWrap(true);


        //setting the location and description of the buy stock button
        buyStock.setBounds(170, 375, 105, 40);
        buyStock.setText("Buy Stock");
        buyStock.setBackground(Color.GREEN);

        //setting the location and description of the sell button
        sellStock.setBounds(510, 375, 105, 40);
        sellStock.setText("Sell Stock");
        sellStock.setBackground(Color.ORANGE);

        //setting the location of the player's stock holdings panel
        playerStockHoldingsPanel.setBounds(80,440, 300,100);
        playerStockHoldingsPanel.setBackground(new Color(0, 0, 0, 125));
        //Doesn't work below TODO
        playerStockHoldingsPanel.add(TradingRoom.playerVsBrotherReports(dayCounter, player, brother, inventory, playerStockHoldingsTextArea));


        //setting the location of the brother's stock holdings panel
        brotherStockHoldings.setBounds(420,440,300,100);
        brotherStockHoldings.setBackground(new Color(0, 0, 0, 125));

        //adding the elements to the frame
        frame.setContentPane(backgroundImg);
        frame.getContentPane().add(welcomeBannerPanel);
        frame.getContentPane().add(welcomeBannerPanelLabel);
        frame.getContentPane().add(tradingTimerPanel);
        frame.getContentPane().add(timeRemaining);
        frame.getContentPane().add(endTradingDay);
        frame.getContentPane().add(breakingNews);
        frame.getContentPane().add(newsTicker);
        frame.getContentPane().add(buyStock);
        frame.getContentPane().add(sellStock);
        frame.getContentPane().add(playerStockHoldingsPanel);
        frame.getContentPane().add(brotherStockHoldings);



        buyStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyMenuCreator();

            }
        });


        sellStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellMenuCreator();

            }
        });


        endTradingDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                bedRoom();
            }
        });
    }

    public void bedRoom() {
        //setting the bedroom background
        bedroomBackground = new ImageIcon("src/main/resources/bedroom.jpg");
        roomBackgroundImg = new JLabel(bedroomBackground);
        //setting the buttons for the computer and sleep
        sleep = new JButton();
        playComputer = new JButton();


        //setting the descriptions for the sleep button
        sleep.setBounds(570, 350, 160, 50);
        sleep.setText("Sleep/Next Day?");
        sleep.setBackground(new Color(0, 0, 85, 125));
        sleep.setForeground(Color.CYAN);


        //setting the descriptions for the play computer button
        playComputer.setBounds(15, 240, 160, 50);
        playComputer.setText("Play Computer Game?");
        playComputer.setBackground(new Color(0, 0, 85, 125));
        playComputer.setForeground(Color.CYAN);


        //setting the bounds of the image background
        roomBackgroundImg.setBounds(0, 0, 800, 600);


        //adding to the frame
        frame.setContentPane(roomBackgroundImg);
        frame.getContentPane().add(sleep);
        frame.getContentPane().add(playComputer);

        //commented out for future use
//        playComputer.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.getContentPane().removeAll();
//                frame.repaint();
//            }
//        });


        sleep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                dayCounter++;
                guiTradingRoom();
            }
        });
    }


    public void buyMenuCreator() {
        //Getting the J things
        buyMenuStocksListing = new JTextArea();
        buyMenuStocksPanel = new JPanel();
        buyMenuPopup = new JFrame();
        submitBuyStockMenuButton = new JButton("Submit");
        cancelBuyStockMenuButton = new JButton("Cancel");
        stockBuySymbol = new JTextField();
        stockBuyQuantity = new JTextField();
        stockPurchaseHeading = new JLabel("Please enter the symbol of the stock you want to purchase:");
        stockPurchaseQuantityHeading = new JLabel("How many shares would you like? (Integers only): ");
        insufficientBuyBalance = new JTextArea();

        //adding the background
        buyMenuBackground = new ImageIcon("");
        buyMenuBackgroundImg = new JLabel(buyMenuBackground);

        //setting the background dimensions
        //setting the background picture and location
        buyMenuBackgroundImg.setBounds(0, 0, 800, 600);

        //Setting the frame to popup
        buyMenuPopup.setVisible(true);
        buyMenuPopup.setTitle("Buy Stock");
        buyMenuPopup.setSize(600, 500);
        buyMenuPopup.setLocationRelativeTo(null);
        buyMenuPopup.setResizable(false);
        buyMenuPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //setting buymenu stock listing location
        buyMenuStocksPanel.setSize(600, 200);
        buyMenuStocksListing.setEditable(false);
        buyMenuStocksPanel.add(TradingRoom.showStockInventory(buyMenuStocksListing));

        //setting the submit button location
        submitBuyStockMenuButton.setBounds(115, 400, 100, 50);
        submitBuyStockMenuButton.setBackground(Color.GREEN);

        //setting the cancel button location
        cancelBuyStockMenuButton.setBounds(365, 400, 100, 50);
        cancelBuyStockMenuButton.setBackground(Color.ORANGE);

        //setting the stock symbol text field
        stockBuySymbol.setBounds(240, 250, 100, 25);

        //setting the heading for the stock name heading
        stockPurchaseHeading.setBounds(120, 220, 400, 25);

        //setting the stock quantity text field
        stockBuyQuantity.setBounds(240, 320, 100, 25);

        //setting the heading for the stock quantity field/heading
        stockPurchaseQuantityHeading.setBounds(140, 290, 700, 25);

        //adding to the content pane
        buyMenuPopup.setContentPane(buyMenuBackgroundImg);
        buyMenuPopup.getContentPane().add(buyMenuStocksPanel);
        buyMenuPopup.getContentPane().add(submitBuyStockMenuButton);
        buyMenuPopup.getContentPane().add(cancelBuyStockMenuButton);
        buyMenuPopup.getContentPane().add(stockBuySymbol);
        buyMenuPopup.getContentPane().add(stockBuyQuantity);
        buyMenuPopup.getContentPane().add(stockPurchaseHeading);
        buyMenuPopup.getContentPane().add(stockPurchaseQuantityHeading);

        cancelBuyStockMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyMenuPopup.dispose();
            }
        });

        submitBuyStockMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stockBought = stockBuySymbol.getText();
                Integer stockQuant = Integer.parseInt(stockBuyQuantity.getText());

                try {
                    TradingRoom.menuOneBuy(dayCounter, stockBought, stockQuant, insufficientBuyBalance);
                    JOptionPane.showMessageDialog(null,insufficientBuyBalance);

                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }


        });

    }


    public void sellMenuCreator() {
        //Getting the J things
        sellMenuStocksListing = new JTextArea();
        sellMenuStocksPanel = new JPanel();
        sellMenuPopup = new JFrame();
        submitSellStockMenuButton = new JButton("Submit");
        cancelSellStockMenuButton = new JButton("Cancel");
        stockSellSymbol = new JTextField();
        stockSellQuantity = new JTextField();
        stockSellHeading = new JLabel("Please enter the symbol of the stock you want to sell: ");
        stockSellQuantityHeading = new JLabel("               Please enter the quantity: ");

        //adding the background
        sellMenuBackground = new ImageIcon("");
        sellMenuBackgroundImg = new JLabel(sellMenuBackground);

        //setting the background dimensions
        //setting the background picture and location
        sellMenuBackgroundImg.setBounds(0, 0, 800, 600);

        //Setting the frame to popup
        sellMenuPopup.setVisible(true);
        sellMenuPopup.setTitle("Sell Stock");
        sellMenuPopup.setSize(600, 500);
        sellMenuPopup.setLocationRelativeTo(null);
        sellMenuPopup.setResizable(false);
        sellMenuPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //setting sell menu stock listing location
        sellMenuStocksPanel.setSize(600,200);
        sellMenuStocksListing.setEditable(false);
        sellMenuStocksPanel.add(SellingRoom.showPlayerHoldings(sellMenuStocksListing));

        //setting the submit button location
        submitSellStockMenuButton.setBounds(115,400,100,50);
        submitSellStockMenuButton.setBackground(Color.GREEN);

        //setting the cancel button location
        cancelSellStockMenuButton.setBounds(365,400,100,50);
        cancelSellStockMenuButton.setBackground(Color.ORANGE);

        //setting the stock symbol text field
        stockSellSymbol.setBounds(240,250,100,25);

        //setting the heading for the stock name heading
        stockSellHeading.setBounds(120,220,400,25);

        //setting the stock quantity text field
        stockSellQuantity.setBounds(240, 320, 100, 25);

        //setting the heading for the stock quantity field/heading
        stockSellQuantityHeading.setBounds(140,290,400,25);

        //adding to the content pane
        sellMenuPopup.setContentPane(sellMenuBackgroundImg);
        sellMenuPopup.getContentPane().add(sellMenuStocksPanel);
        sellMenuPopup.getContentPane().add(submitSellStockMenuButton);
        sellMenuPopup.getContentPane().add(cancelSellStockMenuButton);
        sellMenuPopup.getContentPane().add(stockSellSymbol);
        sellMenuPopup.getContentPane().add(stockSellQuantity);
        sellMenuPopup.getContentPane().add(stockSellHeading);
        sellMenuPopup.getContentPane().add(stockSellQuantityHeading);

        cancelSellStockMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellMenuPopup.dispose();
            }
        });



        submitSellStockMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stockSold = stockSellSymbol.getText();
                String stockQuantity = stockSellQuantity.getText();

                try {
                    SellingRoom.menuTwoSell(dayCounter,stockSold, Integer.parseInt(stockQuantity),insufficientBuyBalance);
                    JOptionPane.showMessageDialog(null,insufficientBuyBalance);
                    sellMenuPopup.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }

}











