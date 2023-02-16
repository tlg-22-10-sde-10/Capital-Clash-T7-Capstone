package game;



import guigamelogic.GameStory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class GuiLogic extends JFrame {

    private static final JFrame frame = new GuiConstructor();

    // Game Buttons
    private JButton newGame;
    private JButton quitGame;
    private JButton continueButton;
    private JButton endTradingDay;
    private JButton buyStock;
    private JButton sellStock;

    // Game Labels
    private JLabel gameTitle_CAPITAL;
    private JLabel gameTitle_CLASH;
    private JLabel gameTitleText;
    private JLabel backgroundImg;
    private JLabel breakingNews;

    //Game Panels
    private JPanel welcomeBanner;
    private JPanel tradingTimer;
    private JPanel newsTicker;


    //Game Text & Panes
    private JTextArea gameStoryText;
    private JScrollPane scrollPane;

    // Game ImageIcons
    private ImageIcon tradingRoomBackground;
    private ImageIcon img;


    public GuiLogic() {
        initGui();
        frame.setVisible(true);
    }

    //Initializing the GUI, new game and quit buttons
    public void initGui() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
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
                int exitPrompt = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit Capital Clash?", "Quit Capital Clash?", JOptionPane.YES_NO_OPTION);
                if (exitPrompt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setVisible(true);
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
        gameStoryText.setBackground(new Color(0,0,0,65));
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
        public void guiTradingRoom(){
            continueButton.setVisible(false);
            //setting the variables and placements
            welcomeBanner = new JPanel();
            tradingTimer = new JPanel();
            endTradingDay = new JButton();
            breakingNews = new JLabel();
            newsTicker = new JPanel();
            buyStock = new JButton();
            sellStock = new JButton();

            tradingRoomBackground = new ImageIcon("src/main/resources/Trading-Room.jpg");
            backgroundImg = new JLabel(tradingRoomBackground);

            //setting the background picture and location
            backgroundImg.setBounds(0, 0, 800,600);

            //setting the welcome banner location and color
            welcomeBanner.setBounds(0,0, 800, 25);
            welcomeBanner.setBackground(new Color(0, 0, 0, 125));

            //setting the trading timer location and color
            tradingTimer.setBounds(40,40,100,20);
            tradingTimer.setBackground(Color.YELLOW);

            //setting the location end trading day/go to room button
            endTradingDay.setBounds(650, 40,105,40);
            endTradingDay.setBackground(Color.GREEN);
            endTradingDay.setText("End Trading Day");

            //setting the location of the breaking news header
            breakingNews.setBounds(330,100,300,50);
            breakingNews.setText("*** BREAKING NEWS ***");

            //setting the location of the news ticker
            newsTicker.setBounds(100, 140, 600,40);
            newsTicker.setBackground(new Color(0, 0, 0, 125));

            //setting the location and description of the buystock button
            buyStock.setBounds(170, 415, 105,40);
            buyStock.setText("Buy Stock");
            buyStock.setBackground(Color.GREEN);

            //setting the location and description of the sell button
            sellStock.setBounds(510, 415, 105,40);
            sellStock.setText("Sell Stock");
            sellStock.setBackground(Color.ORANGE);


            //adding the elements to the frame
            //frame.setContentPane(backgroundImg);
            frame.getContentPane().add(welcomeBanner);
            frame.getContentPane().add(tradingTimer);
            frame.getContentPane().add(endTradingDay);
            frame.getContentPane().add(breakingNews);
            frame.getContentPane().add(newsTicker);
            frame.getContentPane().add(buyStock);
            frame.getContentPane().add(sellStock);

        }


    }

