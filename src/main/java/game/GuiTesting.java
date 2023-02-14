package game;

import ui.GuiLogic;

public class GuiTesting implements Runnable {
    @Override
    public void run() {
       GuiLogic guiLogic = new GuiLogic();

    }


    public static void main(String[] args) {
        GuiTesting guiTesting = new GuiTesting();
        guiTesting.run();

    }

}

