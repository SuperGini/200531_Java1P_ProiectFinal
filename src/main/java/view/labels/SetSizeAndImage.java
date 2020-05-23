package view.labels;

import javax.swing.*;
import java.awt.*;

public class SetSizeAndImage extends JLabel {

    public SetSizeAndImage(int width, int height, String OriginalSizeFilePath) {
        this.setSize(width, height);
        ImageIcon imageIcon = new ImageIcon(OriginalSizeFilePath);
        this.setIcon(imageIcon);
    }

    public SetSizeAndImage(int x, int y, int width, int height, String resizedImageFilePath){
        this.setBounds( x, y ,width, height);
        Image image = new ImageIcon(resizedImageFilePath)
                .getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);
        this.setIcon(imageIcon);
    }
}
