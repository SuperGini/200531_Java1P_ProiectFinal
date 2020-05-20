package view.labels;

import controller.PersonController;
import models.Person;
import view.frames.CentralFrame;
import view.panel.TransparentPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.regex.Pattern;

public class ChangePasswordPage extends JLabel {

    private JPanel transparentPanel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton changePassword;
    private CentralFrame centralFrame;
    private int width = 1125;
    private int height = 750;


    public ChangePasswordPage(){
        setBounds(0,1100,width, height);
        initTransparentPanel();
        initPasswordLabel();
        initPasswordField();
        initConfirmPasswordLabel();
        initConfirmPasswordField();
        initChangePasswordButton();
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

    private void initPasswordLabel(){
        passwordLabel = new JLabel("password");
        passwordLabel.setBounds(175,229,120,25);
        transparentPanel.add(passwordLabel);
    }

    private void initPasswordField(){
        passwordField = new JPasswordField();
        passwordField.setBounds(40,250,320,30);
        transparentPanel.add(passwordField);
    }

    private void initConfirmPasswordLabel(){
        confirmPasswordLabel = new JLabel("confirm password");
        confirmPasswordLabel.setBounds(155,279,120,25);
        transparentPanel.add(confirmPasswordLabel);

    }

    private void initConfirmPasswordField(){
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(40,300,320,30);
        transparentPanel.add(confirmPasswordField);
    }

    private void initChangePasswordButton(){
        changePassword = new JButton("CHANGE PASSWORD");
        changePassword.setBounds(40,350,320,30);
        changePassword.setBackground(Color.CYAN);
        transparentPanel.add(changePassword);
    }

    public boolean validPassword(){
        if(String.valueOf(passwordField.getPassword()).equals("")){
            showMessage("Enter password");
            return false;
        }

        if(String.valueOf(confirmPasswordField.getPassword()).equals("")){
            showMessage("Enter confirm password");
            return false;
        }

        if(!String.valueOf(passwordField.getPassword()).equals(String.valueOf(confirmPasswordField.getPassword()))){
            showMessage("Passwords dont match");
            return false;
        }

        if(!validPasswordFormat(String.valueOf(passwordField.getPassword()))){
            showMessage("Password must have atleast 6 charters, containes one small letter"
                    + " one big letter and onde number");
            return false;
        }
            showMessage("Password changed");
        return true;
    }

    private boolean validPasswordFormat(String password){
        String passwordRegex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        Pattern pat = Pattern.compile(passwordRegex);
            return pat.matcher(password).matches();
    }


    public void showMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Error",JOptionPane.ERROR_MESSAGE);
    }

    public JButton getChangePassword() {
        return changePassword;
    }

    public void updatePassword(){
        Optional<Person> person = PersonController.getInstance().getLoggedPerson();
        person.ifPresent(value -> PersonController.getInstance().updatePassword(String.valueOf(passwordField.getPassword()), value.getUsername()));

    }
}
