package view.buttons;

import javax.swing.*;

public class Xbutton extends JButton {


    private String gif = "./src/main/resources/icons/x.gif";
    public Xbutton(int x, int y){
        this.setBounds(x, y, 28,27);
        ImageIcon imageIcon = new ImageIcon(gif);
        this.setContentAreaFilled(false);
        this.setIcon(imageIcon);
    }


}
