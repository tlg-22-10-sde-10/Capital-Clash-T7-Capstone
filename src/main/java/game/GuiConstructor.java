package game;

import javax.swing.*;


public class GuiConstructor extends JFrame {

   public GuiConstructor(){
       setTitle("Welcome to: Capital Clash 2.0 (Team 7)");
       setSize(800,620);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout(null);
       setVisible(true);
       setResizable(false);

       Images backgroundImage = new Images("stockimage.png");
       backgroundImage.setBounds(0,0,this.getWidth(),this.getHeight());
       setContentPane(backgroundImage);
   }
}

