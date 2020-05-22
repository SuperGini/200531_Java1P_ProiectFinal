package view.buttons;

import javax.swing.*;
import java.awt.*;

public class MiniButtons extends JButton {



    public MiniButtons(int x, int y, String gif){
        this.setBounds(x, y, 27,27);
        ImageIcon imageIcon = new ImageIcon(gif);
        this.setContentAreaFilled(false);
        this.setIcon(imageIcon);
    }

    public MiniButtons(int x, int y){

        this.setBounds(x, y, 25,25);
        Image image = new ImageIcon("./src/main/resources/icons/red.png")
                .getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);
        this.setContentAreaFilled(false);
        this.setIcon(imageIcon);
    }
}
