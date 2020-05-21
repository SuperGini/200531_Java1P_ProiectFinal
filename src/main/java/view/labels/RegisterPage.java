package view.labels;

import controller.AuditController;
import controller.PersonController;
import models.Audit;
import models.Person;
import util.MouseAction;
import view.panel.TransparentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Optional;
import java.util.regex.Pattern;

public class RegisterPage extends JLabel {

    private JButton loginButton;
    private JButton registerButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel emailAdressLabel;
    private JLabel centerGif;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailAdressField;
    private Person person;
    Optional<Person> person1;
    private int width = 1125;
    private int height = 750;
    private JPanel transparentPanel;
    private MouseListener mouseAction = new MouseAction();

    public RegisterPage() {
        this.setBounds(0,-1100,width, height);
        initTransparentPanel();
//        initCenterGif();
        initUsernameLabel();
        initUsernameField();
        initPasswordLabel();
        initPasswordField();
        initConfirmPasswordLabel();
        initConfirmPasswordField();
        initEmailAdressLabel();
        initEmailAdressField();
        initLoginButton();
        initRegisterButton();

    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
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
//        String gif = "./src/main/resources/icons/nebula.gif";
//        centerGif = new JLabel();
//        centerGif.setBounds(150, 50, 100,100);
//        ImageIcon imageIcon = new ImageIcon(gif);
//        centerGif.setOpaque(false);
//        centerGif.setIcon(imageIcon);
//        transparentPanel.add(centerGif);
//    }

    private void initUsernameLabel(){
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(175,129,100,25);
        transparentPanel.add(usernameLabel);
    }

    private void initUsernameField(){
        usernameField = new JTextField();
        usernameField.setBounds(40,150,320,30);
        transparentPanel.add(usernameField);
    }

    private void initPasswordLabel(){
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(175,179,100,25);
        transparentPanel.add(passwordLabel);
    }

    private void initPasswordField(){
        passwordField = new JPasswordField();
        passwordField.setBounds(40,200,320,30);
        transparentPanel.add(passwordField);
    }

    private void initConfirmPasswordLabel(){
        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(155,229,120,25);
        transparentPanel.add(confirmPasswordLabel);
    }

    private void initConfirmPasswordField(){
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(40,250,320,30);
        transparentPanel.add(confirmPasswordField);

    }

    private void initEmailAdressLabel(){
        emailAdressLabel = new JLabel("Email Adress");
        emailAdressLabel.setBounds(170,279,120,25);
        transparentPanel.add(emailAdressLabel);
    }

    private void initEmailAdressField(){
        emailAdressField = new JTextField();
        emailAdressField.setBounds(40,300,320,30);
        transparentPanel.add(emailAdressField);
    }

    private void initLoginButton(){
        Font noUnderline = new Font("Dialog", Font.PLAIN, 12);
        loginButton = new JButton("Go To Login Page");
        loginButton.setBounds(130,450,140,30);
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        transparentPanel.add(loginButton);
        loginButton.setFont(noUnderline);
        loginButton.addMouseListener(mouseAction);
    }

    private void initRegisterButton(){
        registerButton = new JButton("Register");
        registerButton.setBounds(40,350,320,30);
        registerButton.setBackground(Color.CYAN);
        registerButton.setActionCommand(" account created on: ");
        transparentPanel.add(registerButton);
    }

    public void addUsername(){
        String username = usernameField.getText();
        String emailAdress = emailAdressField.getText();
        String password = new String(passwordField.getPassword());

        person  = new Person(0,username, emailAdress,password);
        boolean personAdded = PersonController.getInstance().create(person);
        if(personAdded){
            JOptionPane.showMessageDialog(null,"Registration complete");
            person1 = PersonController.getInstance().findByUsername(usernameField.getText());
            resetFields();
        }else{
            JOptionPane.showMessageDialog(null, "User allready exists");
        }
    }


            //todo un optional de persoana pentru a lua id-ul din el;
    public void createAuditLog(){
        String username = person1.get().getUsername();
        String action = registerButton.getActionCommand();
        int personId = person1.get().getId();

        Audit audit = new Audit.Builder()
                                .setUsername(username)
                                .setAction(action)
                                .setPersonId(personId)
                                .build();

        AuditController.getInstance().createLog(audit);

    }

    public boolean validRegisterFields(){
        if(usernameField.getText().equals("")){
            showMessage("Enter username");
            return false;
       }else if(String.valueOf(passwordField.getPassword()).equals("")){
            showMessage("Enter Password");
            return false;
        }else if(String.valueOf(confirmPasswordField.getPassword()).equals("")){
            showMessage("Confirm password");
            return false;
        }else if(emailAdressField.getText().equals("")){
            showMessage("Enter email adress");
            return false;
        }else if(!String.valueOf(passwordField.getPassword()).equals(String.valueOf(confirmPasswordField.getPassword()))){
            showMessage("Passwords don't match");
            return false;
        }else if(!validEmailAdress(emailAdressField.getText())){
            showMessage("Enter valid email adress");
            return false;
        }else if(!validPassword(String.valueOf(passwordField.getPassword()))){
            showMessage("Incorect password format");
            return false;
        }else if(PersonController.getInstance().getPersonDao().findByUsername(usernameField.getText()).isPresent()){
            showMessage("Incorect username or password");
            return false;
        }

        return true;
    }



    private void showMessage(String msg){
        JOptionPane.showMessageDialog(null,msg,"Error",JOptionPane.ERROR_MESSAGE);
    }

    private boolean validEmailAdress(String emailAdress){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if(emailAdress == null){
            return false;
        }
            return pat.matcher(emailAdress).matches();
    }

    private boolean validPassword(String password){
        String passwordRegex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        Pattern pat = Pattern.compile(passwordRegex);
        if(password == null){
            return false;
        }else{
            return pat.matcher(password).matches();
        }
    }

    private void resetFields(){
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        emailAdressField.setText("");
        usernameField.requestFocus();
    }



}
