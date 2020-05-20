package view.buttons;

import javax.swing.*;

public class MinimizeButton extends JButton {

    private String gif = "./src/main/resources/icons/min.gif";
    public MinimizeButton(int x, int y){
        this.setBounds(x, y, 27,27);
        ImageIcon imageIcon = new ImageIcon(gif);
        this.setContentAreaFilled(false);
        this.setIcon(imageIcon);
    }
}
