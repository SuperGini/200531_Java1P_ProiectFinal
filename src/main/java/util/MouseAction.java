package util;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class MouseAction extends MouseAdapter {
    Font noUnderline = new Font("Dialog", Font.PLAIN, 12);

    public MouseAction(){
    }

    public Font underLineFont(){
        Map<TextAttribute, Integer> attributes = new HashMap<>();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        return new Font("Dialog", Font.BOLD, 12).deriveFont(attributes);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component button  = e.getComponent();
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(underLineFont());
        button.setFocusable(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Component button  = e.getComponent();
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(underLineFont());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Component button = e.getComponent();
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(noUnderline);
    }
}
