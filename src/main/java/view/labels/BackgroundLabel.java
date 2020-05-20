package view.labels;

import javax.swing.*;
import java.awt.*;

public class BackgroundLabel extends JLabel {

    public BackgroundLabel(int width, int height, String OriginalSizeFilePath) {
        this.setSize(width, height);
        ImageIcon imageIcon = new ImageIcon(OriginalSizeFilePath);
        this.setIcon(imageIcon);
    }

    public BackgroundLabel(String resizedImageFilePath, int width, int height){
        this.setSize(width, height);
        Image image = new ImageIcon(resizedImageFilePath)
                .getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);
        this.setIcon(imageIcon);
    }


}
