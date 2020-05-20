package view.panel;

import javax.swing.*;
import java.awt.*;

public class TransparentPanel extends JPanel {

    int x;
    int y;
    int width;
    int height;

    public TransparentPanel(int x, int y, int width, int height){
        setBounds(x, y, width, height);
        setBackground(new Color(255,255,255,100));
        setOpaque(false);
        setLayout(null);
    }

}
