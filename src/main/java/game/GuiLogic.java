package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JPanel;

public class GuiLogic extends javax.swing.JFrame {

    private static final JFrame frame = new GuiConstructor();
    private JButton newGame;
    private JButton quit;

    Image image = Toolkit.getDefaultToolkit().getImage("gui.background_image.jpeg");

    //Initializing the GUI, new game and quit buttons
    public void initializeGui() throws IOException {

        newGame = new JButton("New Game");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        });
        pack();
        setVisible(true);

    }


}

