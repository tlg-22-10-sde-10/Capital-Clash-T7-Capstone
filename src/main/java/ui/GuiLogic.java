package ui;
import game.Game;
import game.GameClient;
import game.GuiClient;
import game.GuiTesting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class GuiLogic {

    private static final JFrame frame = new GuiClient();
    private JButton newGame;
    private JButton quit;


    //Initializing the GUI, new game and quit buttons
    public void initializeGui() {

        newGame = new JButton("New Game");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


    }


}
