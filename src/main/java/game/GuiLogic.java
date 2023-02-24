package game;

import guigamelogic.CountdownTimer;
import guigamelogic.GameStory;
import guigamelogic.SellingRoom;
import guigamelogic.TradingRoom;
import marketreturn.MarketReturnGenerator;
import players.Computer;
import players.Player;
import random.RandomNumberForNews;
import stock.Stock;
import storage.StockInventory;
import ui.GlobalMethodsAndAttributes;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static ui.GlobalMethodsAndAttributes.*;


public class GuiLogic extends javax.swing.JFrame {

    private static final JFrame frame = new GuiConstructor();
    MarketReturnGenerator mkt = new MarketReturnGenerator();
    private int newsIndexOfTheDay = RandomNumberForNews.getRandomNumber();
    private double marketReturn;

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

    private JLabel stockPurchaseHeading;
    private JLabel stockPurchaseQuantityHeading;
    private JLabel stockSellHeading;
    private JLabel stockSellQuantityHeading;

    //Game Panels
    private JPanel welcomeBannerPanel;
    private JTextArea newsTicker;
    private JScrollPane newsScrollPane;
    private JPanel buyMenuStocksPanel;
    private JPanel sellMenuStocksPanel;
    private JPanel brotherStockHoldings;
    private JPanel playerStockHoldingsPanel;
    private JPanel tradingRoomStockPanel;

    //Game Text & Panes
    private JTextArea tradingRoomStockPanelTextArea;
    private JTextArea gameStoryText;
    private JTextArea playerStockHoldingsTextArea;
    private JTextArea brotherStockHoldingsTextArea;
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

    //Clip for music
    private static Clip backgroundMusicClip;
    private static Clip soundEffectClip;

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

        newGame.setBounds(250, 520, 105, 40);
        newGame.setBackground(Color.GREEN);

        quitGame.setBounds(440, 520, 105, 40);
        quitGame.setBackground(Color.RED);

        gameTitle_CAPITAL.setBounds(165, 30, 300, 50);
        gameTitle_CAPITAL.setFont(new Font("Playfair Display", Font.BOLD, 48));

        gameTitle_CLASH.setBounds(395, 30, 300, 50);
        gameTitle_CLASH.setFont(new Font("Playfair Display", Font.BOLD, 48));
        gameTitle_CLASH.setForeground(Color.RED);

        gameTitleText.setBounds(290, 480, 300, 40);
        gameTitleText.setFont(new Font("Playfair Display", Font.BOLD, 14));

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
                try {
                    guiGameStory();
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

    public void guiGameStory() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
        }
        backgroundMusicClip = openAudioClip("gui_game_music.wav");
        backgroundMusicClip.loop(99);

        newGame.setVisible(false);
        gameStoryText = new JTextArea(GameStory.displayGameInfo());
        scrollPane = new JScrollPane(gameStoryText);
        continueButton = new JButton("Continue");
        JPanel panel = new JPanel();

        img = new ImageIcon(loadImage("money-mistakes-300x295.jpg"));
        backgroundImg = new JLabel(img);

        Border gameStoryBorder = BorderFactory.createLineBorder(Color.RED,2);

        Dimension size = backgroundImg.getPreferredSize();
        backgroundImg.setBounds(450, 110, size.width, size.height);
        panel.setLayout(null);
        panel.add(backgroundImg);

        gameStoryText.setBounds(60, 100, 350, 350);
        gameStoryText.setFont(new Font("Playfair Display", Font.BOLD, 12));
        gameStoryText.setBackground(new Color(0, 0, 0, 0));
        gameStoryText.setBorder(gameStoryBorder);
        gameStoryText.setForeground(Color.black);
        gameStoryText.setEditable(false);
        gameStoryText.setLineWrap(true);
        gameStoryText.setWrapStyleWord(true);

        scrollPane.getViewport().setOpaque(false);

        continueButton.setBounds(650, 500, 105, 40);
        continueButton.setFont(new Font("Playfair Display", Font.BOLD, 12));
        continueButton.setBackground(Color.GREEN);

        frame.setContentPane(panel);
        frame.getContentPane().add(gameStoryText);
        frame.getContentPane().add(continueButton);

        // Continue Game Button Event Listener
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();

                //starts the music once enters the room
                if (backgroundMusicClip != null) {
                    backgroundMusicClip.stop();
                }
                try {
                    backgroundMusicClip = openAudioClip("trading_room_background.wav");
                    // Get the FloatControl object for controlling the volume
                    FloatControl gainControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);

                    // Set the volume to a lower level (in dB)
                    gainControl.setValue(-15.0f); // reduce volume by 10 dB

                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                backgroundMusicClip.loop(99);


                try {
                    guiTradingRoom();
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

    public void guiTradingRoom() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        continueButton.setVisible(false);

        //setting the variables and placements
        welcomeBannerPanel = new JPanel();
        welcomeBannerPanelLabel = new JLabel();
        endTradingDay = new JButton();
        breakingNews = new JLabel();
        buyStock = new JButton();
        sellStock = new JButton();
        brotherStockHoldings = new JPanel();
        playerStockHoldingsPanel = new JPanel();
        playerStockHoldingsTextArea = new JTextArea(6, 28);
        brotherStockHoldingsTextArea = new JTextArea(6, 28);
        tradingRoomStockPanel = new JPanel();
        tradingRoomStockPanelTextArea = new JTextArea();

        //changes the background image
        tradingRoomBackground = new ImageIcon();
        backgroundImg = new JLabel(tradingRoomBackground);

        //create line factory border
        Border border = BorderFactory.createLineBorder(Color.BLUE,2);

        //setting the text to go into the welcome banner
        welcomeBannerPanelLabel.setText(" Welcome to Trading Day: " + dayCounter);
        welcomeBannerPanelLabel.setForeground(Color.black);
        welcomeBannerPanelLabel.setFont(new Font("Playfair Display", Font.BOLD, 18));
        welcomeBannerPanelLabel.setBounds(270, 3,800,20);

        //setting the welcome banner location and color
        welcomeBannerPanel.setBounds(0, 0, 800, 25);
        welcomeBannerPanel.setBackground(new Color(0, 0, 0, 0));

        //setting the location end trading day/go to room button
        endTradingDay.setBounds(620, 510, 150, 40);
        endTradingDay.setBackground(Color.GREEN);
        endTradingDay.setText("End Trading Day");

        //setting the location of the breaking news header
        breakingNews.setBounds(310, 20, 300, 50);
        breakingNews.setText("*** BREAKING NEWS ***");
        breakingNews.setFont(new Font("Playfair Display", Font.BOLD, 14));
        breakingNews.setForeground(Color.red);

        //setting the location of the news ticker
        int newsIndexOfTheDay = RandomNumberForNews.getRandomNumber();
        newsTicker = new JTextArea(" - " + news.getNewsContent(newsIndexOfTheDay));
        newsScrollPane = new JScrollPane(newsTicker);
        newsScrollPane.getViewport().setOpaque(false);
        newsTicker.setBounds(100, 65, 600, 35);
        newsTicker.setFont(new Font("Playfair Display", Font.BOLD, 12));
        newsTicker.setBackground(new Color(0, 0, 0, 65));
        newsTicker.setForeground(Color.blue);
        newsTicker.setEditable(false);
        newsTicker.setLineWrap(true);


        //setting the location and description of the buy stock button
        buyStock.setBounds(280, 325, 105, 40);
        buyStock.setText("Buy Stock");
        buyStock.setBackground(Color.GREEN);

        //setting the location and description of the sell button
        sellStock.setBounds(410, 325, 105, 40);
        sellStock.setText("Sell Stock");
        sellStock.setBackground(Color.ORANGE);


        //setting the location of the trading room's stock holdings panel
        JTable table = TradingRoom.showStocks();
        tradingRoomStockPanel.setBounds(120,110,560,200);
        tradingRoomStockPanel.setBackground(new Color(0,0,0,0));
        tradingRoomStockPanel.setBorder(border);
        tradingRoomStockPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tradingRoomStockPanel.add(table, BorderLayout.CENTER);

        //setting the location of the player's stock holdings panel
        playerStockHoldingsPanel.setBounds(70,380, 320,100);
        playerStockHoldingsPanel.add(TradingRoom.playerReport(dayCounter, player, inventory, playerStockHoldingsTextArea));
        playerStockHoldingsPanel.setBorder(border);

        //setting the location of the brother's stock holdings panel
        brotherStockHoldings.setBounds(405,380,320,100);
        brotherStockHoldings.add(TradingRoom.brotherReport(dayCounter, brother, inventory, brotherStockHoldingsTextArea));
        brotherStockHoldings.setBorder(border);

        //adding the elements to the frame
        frame.setContentPane(backgroundImg);
        frame.getContentPane().add(tradingRoomStockPanel);
        frame.getContentPane().add(welcomeBannerPanel);
        frame.getContentPane().add(welcomeBannerPanelLabel);
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
                try {
                    bedRoom();
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

    public void bedRoom() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //setting the bedroom background
        bedroomBackground = new ImageIcon(loadImage("bedroom.jpg"));
        roomBackgroundImg = new JLabel(bedroomBackground);


        //setting the buttons for the computer and sleep
        sleep = new JButton();
        playComputer = new JButton();

        backgroundMusicClip.stop();
        if (soundEffectClip != null) {
            soundEffectClip.stop();
        }
        soundEffectClip = openAudioClip("door_closing.wav");
        soundEffectClip.start();



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
                updateStocks();
                try {
                    tradingDaysEnd(dayCounter);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                soundEffectClip.stop();
                //starts the music once enters the room
                if (backgroundMusicClip != null) {
                    backgroundMusicClip.stop();
                }
                try {
                    backgroundMusicClip = openAudioClip("trading_room_background.wav");

                    // Get the FloatControl object for controlling the volume
                    FloatControl gainControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);

                    // Set the volume to a lower level (in dB)
                    gainControl.setValue(-15.0f); // reduce volume by 10 dB

                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                backgroundMusicClip.loop(99);



                try {
                    guiTradingRoom();
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

        //setting buy menu stock listing location
        JTable buyMenuPopupTable = TradingRoom.showStocks();
        buyMenuStocksPanel.setSize(600, 200);
        buyMenuStocksListing.setEditable(false);
        buyMenuStocksPanel.add(buyMenuPopupTable .getTableHeader(), BorderLayout.NORTH);
        buyMenuStocksPanel.add(buyMenuPopupTable , BorderLayout.CENTER);

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
                String stockBought = stockBuySymbol.getText().toUpperCase();
                Integer stockQuant = Integer.parseInt(stockBuyQuantity.getText());

                try {
                    TradingRoom.menuOneBuy(dayCounter, stockBought, stockQuant, insufficientBuyBalance);
                    JOptionPane.showMessageDialog(null,insufficientBuyBalance);
                    TradingRoom.playerReport(dayCounter, player, inventory, tradingRoomStockPanelTextArea);
                    if (soundEffectClip != null) {
                        soundEffectClip.stop();
                    }
                    soundEffectClip = openAudioClip("cashier.wav.wav");
                    soundEffectClip.start();
                    guiTradingRoom();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
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
                String stockSold = stockSellSymbol.getText().toUpperCase();
                String stockQuantity = stockSellQuantity.getText();

                try {
                    SellingRoom.menuTwoSell(dayCounter,stockSold, Integer.parseInt(stockQuantity),insufficientBuyBalance);
                    JOptionPane.showMessageDialog(null,insufficientBuyBalance);
                    sellMenuPopup.dispose();
                    if (soundEffectClip != null) {
                        soundEffectClip.stop();
                    }
                    soundEffectClip = openAudioClip("sell.wav");
                    soundEffectClip.start();
                    guiTradingRoom();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }


    public void tradingDaysEnd(int dayCounter) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

        double totalPlayerBalance = player.getAccount().getCashBalance();
        double totalBrotherBalance = brother.getAccount().getCashBalance();

        if (dayCounter == 5) {
            totalPlayerBalance += calculatePriceFromMap(playerStockMap);
            totalBrotherBalance += calculatePriceFromMap(brotherStockMap);

            if (totalPlayerBalance > totalBrotherBalance) {
                backgroundMusicClip.stop();
                if (soundEffectClip != null) {
                    soundEffectClip.stop();
                }
                soundEffectClip = openAudioClip("piglevelwin2mp3-14800.wav");
                soundEffectClip.start();
                JOptionPane.showInternalMessageDialog(null, "You WIN, The Company is yours! \n " + "Your final balance total is $" + String.format("%.02f",totalPlayerBalance) + "\n" + "Your brother's final balance is $" + String.format("%.02f", totalBrotherBalance));
                frame.dispose();

            } else if (totalPlayerBalance < totalBrotherBalance) {
                backgroundMusicClip.stop();
                if (soundEffectClip != null) {
                    soundEffectClip.stop();
                }
                soundEffectClip = openAudioClip("sadTrombone(1).wav");
                soundEffectClip.start();
                JOptionPane.showInternalMessageDialog(null, "You LOSE, the future CEO is your brother! \n" + "Your final balance total is $" + String.format("%.02f",totalPlayerBalance) + "\n" + "Your brother's final balance is $" + String.format("%.02f", totalBrotherBalance));
                GlobalMethodsAndAttributes.playAudio("sadTrombone(1).wav");
                frame.dispose();

            } else {
                backgroundMusicClip.stop();
                if (soundEffectClip != null) {
                    soundEffectClip.stop();
                }
                soundEffectClip = openAudioClip("sadTrombone(1).wav");
                soundEffectClip.start();
                JOptionPane.showInternalMessageDialog(null, "You tied with your brother? Your father decided to keep the company... \n" + "Your final balance total is $" + String.format("%.02f",totalPlayerBalance) + "\n" + "Your brother's final balance is $" + String.format("%.02f", totalBrotherBalance));
                frame.dispose();
            }

        } else if (dayCounter == 4) {
            JOptionPane.showInternalMessageDialog(null, "Tomorrow is your last day of trading, make it count!");
        }
        backgroundMusicClip.stop();
    }

    public void updateStocks(){
        int newIndex = newsIndexOfTheDay;
        double mktReturn = mkt.nextMarketReturn(newIndex);

        for(Stock stock : inventory.getAllStocks()){
            double nextPrice = stock.UpdateStockPriceForTheDay(stock.getCurrentPrice(),
                    mktReturn,newIndex);
            stock.setCurrentPrice(nextPrice);
        }

    }


    private Image loadImage (String fileName){
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {

            //noinspection DataFlowIssue
            return ImageIO.read(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static Clip openAudioClip(String clipname) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = GuiLogic.class.getClassLoader().getResourceAsStream(clipname);
        //noinspection ConstantConditions
        InputStream buffer = new BufferedInputStream(inputStream);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(buffer);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

}



















