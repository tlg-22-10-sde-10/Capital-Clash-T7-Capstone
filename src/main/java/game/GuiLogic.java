package game;

import ui.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiLogic extends javax.swing.JFrame {

    private static final JFrame frame = new GuiConstructor();

    // Game Buttons
    private JButton newGame;
    private JButton quitGame;
    private JButton continueButton;

    // Game Labels
    private JLabel gameTitle_CAPITAL;
    private JLabel gameTitle_CLASH;
    private JLabel gameTitleText;
    private JLabel backgroundImg;

    private JTextArea gameStoryText;
    private JScrollPane scrollPane;

    // Game ImageIcons
    private ImageIcon img;

    public GuiLogic(){
        initGui();
        frame.setVisible(true);
    }

    //Initializing the GUI, new game and quit buttons
    public void initGui() {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
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
    }

    public void guiGameStory(){
        newGame.setVisible(false);
        gameStoryText = new JTextArea("Testing 123");
        scrollPane = new JScrollPane(gameStoryText);
        continueButton = new JButton("Continue");

        img = new ImageIcon("src/main/resources/gui_background_image.jpeg");
        backgroundImg = new JLabel(img);
        backgroundImg.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

        gameStoryText.setBounds(75, 60, 350, 350);
        gameStoryText.setFont(new Font("Playfair Display", Font.BOLD, 12));
        gameStoryText.setBackground(new Color(0,0,0,65));
        gameStoryText.setForeground(Color.white);
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
    }
}

