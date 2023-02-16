package game;

import ui.GlobalMethodsAndAttributes;
import ui.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.GlobalMethodsAndAttributes.*;
import static ui.GlobalMethodsAndAttributes.ANSI_RESET;

public class GuiLogic extends JFrame {

    private static final JFrame frame = new GuiConstructor();

    // Game Buttons
    private JButton newGame;
    private JButton quitGame;

    // Game Labels
    private JLabel gameTitle_CAPITAL;
    private JLabel gameTitle_CLASH;
    private JLabel gameTitleText;
    private JLabel backgroundImg;

    // Game ImageIcons
    private ImageIcon img;

    // Game Panels
    private JPanel gameStory;

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

        // Button Event Listeners
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String button = e.getActionCommand();
                if (button.equals("New Game")) {
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    guiGameStory();
                }
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
        gameStory = new JPanel();

        img = new ImageIcon("src/main/resources/Wall-street.jpg");
        backgroundImg = new JLabel(img);
        backgroundImg.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

        gameStory.setBounds(130, 130, 350, 350);
        gameStory.setBackground(new Color(0, 0, 0, 125));

        frame.setContentPane(backgroundImg);
        frame.getContentPane().add(gameStory);
    }
}
