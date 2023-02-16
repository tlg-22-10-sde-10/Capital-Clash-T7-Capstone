package game;

import java.io.IOException;

public class GuiClient implements Runnable {
    @Override
    public void run() {
       GuiLogic guiLogic = new GuiLogic();
       guiLogic.initGui();
    }


    public static void main(String[] args) {
        GuiClient guiTesting = new GuiClient();
        guiTesting.run();

    }
}

