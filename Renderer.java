package pong;

import javax.swing.JPanel;
import java.awt.*;

public class Renderer extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Pong.pong.render((Graphics2D) g);

    }

}
//ctr alt L
