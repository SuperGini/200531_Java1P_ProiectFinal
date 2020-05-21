package view.frames;

import AppPackage.AnimationClass;
import controller.AuditController;
import media.Picture;
import util.LogOutFunction;
import view.buttons.MinimizeButton;
import view.buttons.Xbutton;
import view.labels.*;
import view.menubar.MenuBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    //TODO sa vedem daca mai e nevoie sa fie CentralFrame singleton class

public class CentralFrame extends JFrame {


    private BackgroundLabel backgroundLabel;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private MainPage mainPage;
    private AddFlightsPage addFlightsPage;
    private MyAccountPage myAccountPage;
    private ChangePasswordPage changePasswordPage;
    private AuditPage auditPage;
    private AuditController log = AuditController.getInstance();


    private JPanel panel;
    private MenuBar menuBar;
    private JLabel menuBarLabel;
    private Xbutton xButton;
    private MinimizeButton minimizeButton;
    private JButton backButton;
    private Picture picture;
    private List<JLabel> pages;
    private int count;
    private Timer timer5;
    private int width = 1125;
    private int height = 750;
    private int posX =0, posY = 0;
    private  Path filePath = Paths.get("./src/main/resources/images");

    private AnimationClass slideEfect = new AnimationClass();
    private Random random = new Random();
    private List<JLabel> labelsBackButton = new ArrayList<>();



    private CentralFrame(){
        initFrame();
        initBackgroundLabel();
        initMenuBar();
        initLoginPage();
        initRegisterPage();
        initMainPage();
        initAddFlightsPage();
        initMyAccountPage();
        initChangePasswordPage();
        initAuditPage();
        initMinimButton();
        initXbutton();
        initBackButton();
        mouseListener();
        setVisible(true);
    }

    private void initFrame(){
        setSize(width, height);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel  = new JPanel();
        panel.setSize(width, height);
        panel.setLayout(null);
        add(panel);
    }

    private String getRandomBacgroundPicture(){
        picture = new Picture();
        java.util.List<String> strings = picture.getPictures(filePath);
        return  strings.get(random.nextInt(strings.size()));
    }

    private void initBackgroundLabel(){
        backgroundLabel = new BackgroundLabel(getRandomBacgroundPicture(),width, height);
        panel.add(backgroundLabel);
    }

    private void initMenuBar(){
        menuBar = new MenuBar();
        menuBarLabel = new JLabel();
        menuBarLabel.setBounds(0,-27,1070,27);
        menuBarLabel.add(menuBar);
        backgroundLabel.add(menuBarLabel);

        menuBar.getHomePage().addActionListener( e -> menuBarHomePage());
        menuBar.getMyAccount().addActionListener(e -> menuBarMyAccountPage());
        menuBar.getLogOut().addActionListener( e-> menuBarLogOut());
    }

    private void initLoginPage(){
        loginPage = new LoginPage();
        backgroundLabel.add(loginPage);

        loginPage.getLoginButton().addActionListener(e-> loginPageLoginButton());
        loginPage.getRegisterButton().addActionListener(e -> moveLoginRegisterPage(loginPage));
    }

    private void initRegisterPage(){
        registerPage = new RegisterPage();
        backgroundLabel.add(registerPage);

        registerPage.getRegisterButton().addActionListener(e -> registerPageRegisterButton());
        registerPage.getLoginButton().addActionListener(e -> moveLoginRegisterPage(registerPage));
    }

    private void initMainPage(){
        mainPage = MainPage.getInstance();
        backgroundLabel.add(mainPage);

        mainPage.getAdaugaZbor().addActionListener(e->addFlightButton());
    }

    private void initMyAccountPage(){
        myAccountPage = MyAccountPage.getInstance();
        backgroundLabel.add(myAccountPage);

        myAccountPage.getChangePasswordButton().addActionListener( e-> myAccoutPageChangePassButton());

        myAccountPage.getAuditPageButton().addActionListener(e-> myAccountPageAuditPageButton());

        myAccountPage.getChangeEmailButton().addActionListener(e ->  myAccountPageChangeEmailButton());

        myAccountPage.getChangeUsernameButton().addActionListener(e->  myAccountPageChangeUsernameButton());
    }


    private void initChangePasswordPage(){
        changePasswordPage = new ChangePasswordPage();
        backgroundLabel.add(changePasswordPage);
        changePasswordPage.getChangePassword().addActionListener(e -> changePasswordPageChangePasswordButton());
    }

    private void initAuditPage(){
        auditPage = new AuditPage();
        backgroundLabel.add(auditPage);
    }

    private void initAddFlightsPage(){
        addFlightsPage = new AddFlightsPage();
        backgroundLabel.add(addFlightsPage);
        addFlightsPage.getAnulateButton().addActionListener(e-> flightPageAnulateButton());
        addFlightsPage.getAddFlightButton().addActionListener(e -> flightPageAddFlightButton());
    }

    private void initMinimButton(){
        minimizeButton = new MinimizeButton(1069,0);
        backgroundLabel.add(minimizeButton);
        minimizeButton.addActionListener(e -> this.setExtendedState(JFrame.ICONIFIED));
    }

    private void initXbutton(){
        xButton = new Xbutton(1097,0);
        backgroundLabel.add(xButton);
        xButton.addActionListener( e-> dispose());
    }

    private void mouseListener() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt) {

                setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);

            }
        });
    }

    public void initBackButton(){
        backButton = new JButton("back");
        backButton.setBounds(10,30,100,20);
        backgroundLabel.add(backButton);
        backButton.addActionListener(e -> letsGoBack());
    }
    //todo de rezolvat bug backbutton cand dau back si foward si iar back
    // nu o ia de la ultima pagina afisata ci de unde a ramas contorul
    public void letsGoBack(){

        int lastIndex = labelsBackButton.size() - count -1;
        int beforeLastIndex = labelsBackButton.size()  - count - 2;

        if(loginPage.getY() !=0 && labelsBackButton.get(lastIndex).getY() == 0){  //anti spam button:D
            count++;
     //       System.out.println(count + "  : " + labelsBackButton.size());

            if(lastIndex  > 1){
                oneLabelUpOneLabelDown(labelsBackButton.get(beforeLastIndex));
            }

            if(lastIndex == 1){
                moveTwoLabelsDown(loginPage);
            }

            if((lastIndex > 1) && (labelsBackButton.get(beforeLastIndex) == loginPage)){

                moveTwoLabelsDown(loginPage);
            }
        }

        if(loginPage.getY() == 0){
            count = 0;
            labelsBackButton.clear();
        }
    }

    public void moveLoginRegisterPage(JLabel up){
        pages = getPages();
        for(JLabel page : pages){
            if((page.getY() == -1100) && (page != up)){
                slideEfect.jLabelYDown(-1100,0,10,4, page);
                slideEfect.jLabelYUp(0,-1100,10,4, up);
            }
        }
    }


    public Timer getTimer5(){
        timer5 = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                slideEfect.jLabelYUp(0,-1100,10,4, loginPage);
                slideEfect.jLabelYUp(1100,0,10,4, mainPage);
                slideEfect.jLabelYDown(-27,0,1,1,menuBarLabel);
                timer5.stop();
            }
        });
        return timer5;
    }

    public void moveTwoLabelsDown(JLabel down){
        pages = getPages();
        for(JLabel page : pages){
            if((page.getX() == 0) && (page != down)){
                slideEfect.jLabelYDown(0,1100,10,4, page);
                slideEfect.jLabelYDown(-1100,0,10,4, down);
                slideEfect.jLabelYUp(0,-27,1,1,menuBarLabel);
            }
        }
    }

    public void oneLabelUpOneLabelDown(JLabel up){
        pages = getPages();
        for(JLabel page : pages){
            if((page.getY() == 0) && (page != up)){
                slideEfect.jLabelYDown(0,1100,10,4, page);
                slideEfect.jLabelYUp(1100,0,10,4, up);
            }
        }
    }

    public List<JLabel> getPages(){
        pages = new ArrayList<>();
        pages.add(loginPage);
        pages.add(registerPage);
        pages.add(mainPage);
        pages.add(addFlightsPage);
        pages.add(myAccountPage);
        pages.add(changePasswordPage);
        pages.add(auditPage);
        return pages;
    }

    private void menuBarHomePage(){
        if(mainPage.getY() == 1100){
            log.createAuditLog("accesed Main Page on:");
            labelsBackButton.add(mainPage);
            oneLabelUpOneLabelDown(mainPage);
        }
    }

    private void menuBarMyAccountPage(){
        if(myAccountPage.getY() == 1100){
            oneLabelUpOneLabelDown(myAccountPage);
            log.createAuditLog("accessed My Account page on:"); //todo de verificat daca acum e logul ok
            labelsBackButton.add(myAccountPage);
        }
    }

    private void menuBarLogOut(){
        if(loginPage.getY() == -1100){
            moveTwoLabelsDown(loginPage);
            log.createAuditLog("logged out on:");
        }
    }

    private void loginPageLoginButton(){
        if(loginPage.validCredential()){
            count = 0;
            labelsBackButton.clear();
            log.createAuditLog("logged in on");
            log.createAuditLog("accessed Main Page on:");
            getTimer5().start();
            new LogOutFunction().getLogOutTimer().start();
            labelsBackButton.add(loginPage);
            labelsBackButton.add(mainPage);
        }
    }

    private void registerPageRegisterButton(){
        if(registerPage.validRegisterFields()){
            registerPage.addUsername();
            registerPage.createAuditLog();
            moveLoginRegisterPage(registerPage);
        }
    }

    private void addFlightButton(){
        oneLabelUpOneLabelDown(addFlightsPage);
        log.createAuditLog("accesed Add Flight Page on:");
        labelsBackButton.add(addFlightsPage);
    }

    private void myAccoutPageChangePassButton(){
        oneLabelUpOneLabelDown(changePasswordPage);
        log.createAuditLog("accesed Password Change Page on: ");
        labelsBackButton.add(changePasswordPage);
    }

    private void myAccountPageAuditPageButton(){
        oneLabelUpOneLabelDown(auditPage);
        auditPage.initAuditTableData();
        log.createAuditLog("accesed Audit Page");
        labelsBackButton.add(auditPage);
    }

    private void myAccountPageChangeEmailButton(){
        if(myAccountPage.validEmailAdress()){
            myAccountPage.updateEmailAdress();
        }
    }

    private void myAccountPageChangeUsernameButton(){
        if(myAccountPage.validUsername()){
            myAccountPage.updateUsername();
        }
    }

    private void changePasswordPageChangePasswordButton(){
        if(changePasswordPage.validPassword()){
            changePasswordPage.updatePassword();
            moveTwoLabelsDown(loginPage);
            log.createAuditLog("changed password on:");
        }
    }

    private void flightPageAnulateButton(){
        oneLabelUpOneLabelDown(mainPage);
        log.createAuditLog("exiting Add Flight Page on:");
        log.createAuditLog("accesed Main Page on:");
        labelsBackButton.add(mainPage);
    }

    private void flightPageAddFlightButton(){
        if(addFlightsPage.valid()) {
            addFlightsPage.addFlight();
            mainPage.tableData();
            log.createAuditLog("a flight was added on:");
        }
    }

    private static final class SingletonHolder{
       public static final CentralFrame INSTANCE = new CentralFrame();
    }

    public static CentralFrame getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

}
