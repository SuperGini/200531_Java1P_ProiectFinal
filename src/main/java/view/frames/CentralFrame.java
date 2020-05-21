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

        menuBar.getHomePage().addActionListener( e ->{
            if(mainPage.getY() == 1100){
                log.createAuditLog("accesed Main Page on:");
                labelsBackButton.add(mainPage);
                menuBarMoveFrames(getMainPage());
            }

        });
        menuBar.getMyAccount().addActionListener(e ->{

                if(myAccountPage.getY() == 1100){
                    menuBarMoveFrames(getMyAccountPage());
                    log.createAuditLog("accessed My Account page on:"); //todo de verificat daca acum e logul ok
                    labelsBackButton.add(myAccountPage);
                    }
                });

        menuBar.getLogOut().addActionListener( e->{
            if(loginPage.getY() == -1100){
                setMenuBarLogout(loginPage);
                log.createAuditLog("logged out on:");
            }
        });
    }

    private void initLoginPage(){
        loginPage = new LoginPage();
        backgroundLabel.add(loginPage);

        loginPage.getLoginButton().addActionListener(e->{
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
        });

        loginPage.getRegisterButton().addActionListener(e -> moveLoginRegisterPage(loginPage));
    }

    private void initRegisterPage(){
        registerPage = new RegisterPage();
        backgroundLabel.add(registerPage);

        registerPage.getRegisterButton().addActionListener(e ->{
           if(registerPage.validRegisterFields()){
               registerPage.addUsername();
               registerPage.createAuditLog();
               moveLoginRegisterPage(registerPage);
           }
        });
        registerPage.getLoginButton().addActionListener(e -> moveLoginRegisterPage(registerPage));
    }

    private void initMainPage(){
        mainPage = MainPage.getInstance();
        backgroundLabel.add(mainPage);

        mainPage.getAdaugaZbor().addActionListener(e->{
         menuBarMoveFrames(addFlightsPage);
         log.createAuditLog("accesed Add Flight Page on:");
         labelsBackButton.add(addFlightsPage);
        });
    }

    private void initMyAccountPage(){
        myAccountPage = MyAccountPage.getInstance();
        backgroundLabel.add(myAccountPage);

        myAccountPage.getChangePasswordButton().addActionListener( e->{
            menuBarMoveFrames(changePasswordPage);
            log.createAuditLog("accesed Password Change Page on: ");
            labelsBackButton.add(changePasswordPage);
        });

        myAccountPage.getAuditPageButton().addActionListener(e->{
            menuBarMoveFrames(auditPage);
            auditPage.initAuditTableData();
            log.createAuditLog("accesed Audit Page");
            labelsBackButton.add(auditPage);
        });

        myAccountPage.getChangeEmailButton().addActionListener(e ->{
            if(myAccountPage.validEmailAdress()){
                myAccountPage.updateEmailAdress();
            }
        });

        myAccountPage.getChangeUsernameButton().addActionListener(e->{
            if(myAccountPage.validUsername()){
                myAccountPage.updateUsername();
            }
        });
    }


    private void initChangePasswordPage(){
        changePasswordPage = new ChangePasswordPage();
        backgroundLabel.add(changePasswordPage);

        changePasswordPage.getChangePassword().addActionListener(e -> {
            if(changePasswordPage.validPassword()){
                changePasswordPage.updatePassword();
                moveTwoLabelsDown(loginPage);
                log.createAuditLog("changed password on:");
            }
        });
    }

    private void initAuditPage(){
        auditPage = new AuditPage();
        backgroundLabel.add(auditPage);
    }

    private void initAddFlightsPage(){
        addFlightsPage = new AddFlightsPage();
        backgroundLabel.add(addFlightsPage);
        addFlightsPage.getAnulateButton().addActionListener(e->{
            menuBarMoveFrames(mainPage);
            log.createAuditLog("exiting Add Flight Page on:");
            log.createAuditLog("accesed Main Page on:");
            labelsBackButton.add(mainPage);

        });

        addFlightsPage.getAddFlightButton().addActionListener(e ->{
            if(addFlightsPage.valid()) {
                addFlightsPage.addFlight();
                mainPage.tableData();
                log.createAuditLog("a flight was added on:");
            }
        });
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
                menuBarMoveFrames(labelsBackButton.get(beforeLastIndex));
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


    public LoginPage getLoginPage() {
        return loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
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



    public void menuBarMoveFrames(JLabel up){
        pages = getPages();
        for(JLabel page : pages){
                if((page.getY() == 0) && (page != up)){
                    slideEfect.jLabelYDown(0,1100,10,4, page);
                    slideEfect.jLabelYUp(1100,0,10,4, up);

                }
        }
    }

    public void setMenuBarLogout(JLabel down){
        pages = getPages();
        for(JLabel page : pages){
            if(page.getY() == 0 && (page != down)){
                slideEfect.jLabelYDown(-1100,0,10,4, down);
                slideEfect.jLabelYDown(0,1100,10,4, page);
                slideEfect.jLabelYUp(0,-27,1,1,menuBarLabel);
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


    private static final class SingletonHolder{
       public static final CentralFrame INSTANCE = new CentralFrame();
    }

    public static CentralFrame getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public AddFlightsPage getAddFlightsPage() {
        return addFlightsPage;
    }

    public MyAccountPage getMyAccountPage() {
        return myAccountPage;
    }

    public ChangePasswordPage getChangePasswordPage() {
        return changePasswordPage;
    }

    public AuditPage getAuditPage() {
        return auditPage;
    }


}
