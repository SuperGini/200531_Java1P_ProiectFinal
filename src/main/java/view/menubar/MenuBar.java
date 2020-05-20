package view.menubar;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {

    private JMenuItem homePage;
    private JMenuItem myAccount;
    private JMenuItem logOut;
    private JMenu menu;
 

    public MenuBar(){
        this.setBounds(0,0,1070,27);
        initMenu();
        initHomePage();
        initMyAccount();
        initLogOut();

    }

    private void initMenu(){
        menu = new JMenu("Options           ");
        menu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(menu);
    }

    private void initHomePage(){
        homePage = new JMenuItem("Home Page");
        menu.add(homePage);
    }

    private void initMyAccount(){
        myAccount = new JMenuItem("My Account");
        menu.add(myAccount);
    }

    private void initLogOut(){
        logOut = new JMenuItem("Log Out");
        menu.add(logOut);

    }

    public JMenuItem getHomePage() {
        return homePage;
    }

    public JMenuItem getMyAccount() {
        return myAccount;
    }

    public JMenuItem getLogOut() {
        return logOut;
    }
}
