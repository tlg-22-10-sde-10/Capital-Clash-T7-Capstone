package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Images extends JPanel {

    private final Image image;


    public Images(String img) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(img)) {

            //noinspection DataFlowIssue
            this.image = ImageIO.read(input);
            Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
            setSize(size);
            setLayout(null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

    }
}
