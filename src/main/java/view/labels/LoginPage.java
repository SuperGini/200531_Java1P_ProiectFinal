package view.labels;

import controller.PersonController;
import models.Person;
import view.panel.TransparentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Optional;

public class LoginPage extends JLabel {

    private JButton loginButton;
    private JButton registerButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel centerGif;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private MyAccountPage myAccountPage;
    private int width = 1125;
    private int height = 750;
    private JPanel transparentPanel;

    public LoginPage() {
        this.setBounds(0,-1100,width, height);
        initTransparentPanel();
//        initCenterGif();
        initUsernameLabel();
        initUsernameField();
        initPasswordLabel();
        initPasswordField();
        initLoginButton();
        initRegisterButton();

    }


    private void initTransparentPanel(){
        transparentPanel = new TransparentPanel(362,125,400,500){
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        add(transparentPanel);
    }

//    private void initCenterGif(){
//        String gif = "./src/main/resources/icons/pointing.gif";
//        centerGif = new JLabel();
//        centerGif.setBounds(150, 50, 100,100);
//        ImageIcon imageIcon = new ImageIcon(gif);
//
//        centerGif.setOpaque(false);
//        centerGif.setIcon(imageIcon);
//        transparentPanel.add(centerGif);
//    }

    private void initUsernameLabel(){
        usernameLabel = new JLabel("Username/email");
        usernameLabel.setBounds(160,229,100,25);
        transparentPanel.add(usernameLabel);
    }

    private void initUsernameField(){
        usernameField = new JTextField();
        usernameField.setBounds(40,250,320,30);
        transparentPanel.add(usernameField);
    }

    private void initPasswordLabel(){
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(175,279,100,25);
        transparentPanel.add(passwordLabel);
    }

    private void initPasswordField(){
        passwordField = new JPasswordField();
        passwordField.setBounds(40,300,320,30);
        transparentPanel.add(passwordField);
    }

    private void initLoginButton(){
        loginButton = new JButton("Login");
        loginButton.setBounds(40,350,320,30);
        loginButton.setBackground(Color.CYAN);
        transparentPanel.add(loginButton);

    }

    private void initRegisterButton(){
        registerButton = new JButton("Register");
        registerButton.setBounds(40,400,320,30);
        registerButton.setBackground(Color.CYAN);
        transparentPanel.add(registerButton);



        registerButton.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }



    public boolean validCredential(){
        if(usernameField != null){

            Optional<Person> person1 = PersonController.getInstance()
                                    .loginUsername(usernameField.getText(), new String(passwordField.getPassword()));

            Optional<Person> person2 = PersonController.getInstance()
                                    .loginEmailAdress(usernameField.getText(), new String (passwordField.getPassword()));

            if(person1.isPresent()){
                JOptionPane.showMessageDialog(null, "Login complete!");
                myAccountUsernameAndEmail(person1.get().getUsername(),person1.get().getEmailAdress());
                resetFields();
                return true;
            }
            if(person2.isPresent()){
                JOptionPane.showMessageDialog(null, "Login complete!");
                myAccountUsernameAndEmail(person2.get().getUsername(), person2.get().getEmailAdress());
                resetFields();
                return true;
            }
        }
        showMessage("Please enter valid username/email and password");
        resetFields();
        return false;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(null,message,"Error", JOptionPane.ERROR_MESSAGE);
    }

    private void myAccountUsernameAndEmail(String username, String emailAdress){
        myAccountPage = MyAccountPage.getInstance();
        myAccountPage.getChangeUsernameLabel().setText(username);
        myAccountPage.getChangeEmailLabel().setText(emailAdress);
    }

    private void resetFields(){
        usernameField.setText("");
        passwordField.setText("");
        usernameField.requestFocus();
    }
}









