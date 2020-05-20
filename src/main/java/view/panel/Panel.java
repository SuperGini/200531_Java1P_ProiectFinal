package view.panel;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    public Panel(){
        setLayout(null);
        add(button);
        setBounds(0,0,100,25);
        setBackground(Color.red);

    }

    JButton button = new JButton("xxx");

}
