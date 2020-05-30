package view.frames;

import AppPackage.AnimationClass;
import controller.AuditController;
import controller.PersonController;
import media.Picture;
import util.BackAndForward;
import util.LogOutFunction;
import view.buttons.MiniButtons;
import view.labels.*;
import view.menubar.MenuBar;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class CentralFrame extends JFrame {


    private SetSizeAndImage backgroundLabel;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private HomePage homePage;
    private AddFlightsPage addFlightsPage;
    private MyAccountPage myAccountPage;
    private ChangePasswordPage changePasswordPage;
    private AuditPage auditPage;
    private AuditController log = AuditController.getInstance();


    private JPanel panel;
    private MenuBar menuBar;
    private JLabel menuBarLabel;
    private Picture picture;
    private List<JLabel> pages;
    private List<JButton> minimizeButtons;
    private JButton miniButton;
    private Timer timer5;
    private int count2;
    private boolean setListIterator = true;
    private boolean forward = false;
    private boolean back = true;

    private int width = 1125;
    private int height = 750;
    private int posX =0, posY = 0;
    private  Path filePath = Paths.get("./src/main/resources/images");
    private AnimationClass slideEfect = new AnimationClass();
    private ScheduledExecutorService service;
    private Random random = new Random();
    private List<JLabel> backAndForwardList = new LinkedList<>();
    private  ListIterator<JLabel> listIterator;

    private BackAndForward backAndForward = new BackAndForward();



    private CentralFrame(){
        initFrame();
        initBackgroundLabel();
        initMenuBar();
        initLoginPage();
        initRegisterPage();
        initHomePage();
        initAddFlightsPage();
        initMyAccountPage();
        initChangePasswordPage();
        initAuditPage();
        mouseListener();
        initCloseMinimizeBackButton();
        randomImageGenerator();
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
        List<String> strings = picture.getPictures(filePath);
        return   strings.get(random.nextInt(strings.size()));
    }

    private void initBackgroundLabel(){
        backgroundLabel = new SetSizeAndImage(0,0,width, height,getRandomBacgroundPicture());
        panel.add(backgroundLabel);

    }

            // method 1
    private void randomImageGenerator(){
        service = Executors.newSingleThreadScheduledExecutor();
        Runnable r = () -> backgroundLabel.setIcon(imageIcons().get(random.nextInt(imageIcons().size())));
        service.scheduleAtFixedRate(r,60,60, TimeUnit.SECONDS);
    }

        //method 2
    private List<ImageIcon> imageIcons(){
       return picture.getPictures(filePath)
                .stream()
                .map(this::image)
                .collect(Collectors.toList());
    }

        //method 3
    private ImageIcon image(String image){
        Image image1 = new ImageIcon(image)
                .getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        return new ImageIcon(image1);
    }

    private void initCloseMinimizeBackButton(){
       List<String> miniGifs = picture.getMiniGifs();

        minimizeButtons = new ArrayList<>();

        for(int i = 0; i < 4 ; i++){
            miniButton = new MiniButtons(1017 + i*27,0,miniGifs.get(i));
            minimizeButtons.add(miniButton);
            backgroundLabel.add(miniButton);

        }
        minimizeButtons.get(0).setToolTipText("back");
        minimizeButtons.get(1).setToolTipText("forward");
        minimizeButtons.get(2).setToolTipText("minimize");
        minimizeButtons.get(3).setToolTipText("close");
        minimizeButtons.get(0).addActionListener(e -> backAndForward.hatzInSpateHatzInFata(backAndForwardList, "back" ));
        minimizeButtons.get(1).addActionListener( e-> backAndForward.hatzInSpateHatzInFata(backAndForwardList, "forward"));
        minimizeButtons.get(2).addActionListener(e -> minimizeButton() );
        minimizeButtons.get(3).addActionListener(e -> closeProgram());
    }

    private void minimizeButton(){
        setExtendedState(JFrame.ICONIFIED);
        log.createAuditLog("MINIMIZE PAGE");
    }

    private void initMenuBar(){
        menuBar = new MenuBar();
        menuBarLabel = new JLabel();
        menuBarLabel.setBounds(0,-27,1017,27);
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

    private void initHomePage(){
        homePage = new HomePage();
        backgroundLabel.add(homePage);

        homePage.getAddFlight().addActionListener(e-> mainPageaddFlightButton());
    }

    private void initMyAccountPage(){
        myAccountPage = new MyAccountPage();
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

        addFlightsPage.getCancelButton().addActionListener(e-> flightPageCancelButton());
        addFlightsPage.getAddFlightButton().addActionListener(e -> flightPageAddFlightButton());
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

        timer5 = new Timer(20, e -> {
            count2++;
            if(count2 == 1){
                slideEfect.jLabelYDown(0,40,20,2,loginPage);
            }
            if(count2 == 22){
                slideEfect.jLabelYUp(40,-1100,10,4, loginPage);
                slideEfect.jLabelYUp(1100,0,10,4, homePage);
                log.createAuditLog(loginPage.toString());
                log.createAuditLog(homePage.toString());
            }

            if( count2 == 150){
                slideEfect.jLabelYDown(-27,0,1,1,menuBarLabel);
                timer5.stop();
                count2 =0;
            }
        });
        return timer5;
    }

    public void moveTwoLabelsDown(JLabel down){
        log.createAuditLog("LOGOUT");
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
                log.createAuditLog(up.toString());
            }
        }
    }

    public List<JLabel> getPages(){
        pages = new ArrayList<>();
        pages.add(loginPage);
        pages.add(registerPage);
        pages.add(homePage);
        pages.add(addFlightsPage);
        pages.add(myAccountPage);
        pages.add(changePasswordPage);
        pages.add(auditPage);
        return pages;
    }

    private void menuBarHomePage(){
        if(homePage.getY() == 1100){
            oneLabelUpOneLabelDown(homePage);
            addPageToBackButton(homePage);
        }
    }

    private void menuBarMyAccountPage(){
        if(myAccountPage.getY() == 1100){
            oneLabelUpOneLabelDown(myAccountPage);
            addPageToBackButton(myAccountPage);

        }
    }

    private void menuBarLogOut(){
        if(loginPage.getY() == -1100){
            moveTwoLabelsDown(loginPage);
            backAndForwardList.clear();
            backAndForward.setSetListIterator(true);
        //    setListIterator = true;
        }
    }

    private void loginPageLoginButton(){
        if(loginPage.validCredential()){
            getTimer5().start();
            new LogOutFunction().getLogOutTimer().start();
            addPageToBackButton(loginPage);
            addPageToBackButton(homePage);
        }
    }

    private void registerPageRegisterButton(){
        if(registerPage.validRegisterFields()){
            registerPage.addUsername();
            registerPage.createAuditLog();
            moveLoginRegisterPage(registerPage);
        }
    }

    private void mainPageaddFlightButton(){
        oneLabelUpOneLabelDown(addFlightsPage);
        addPageToBackButton(addFlightsPage);
    }

    private void myAccoutPageChangePassButton(){
        oneLabelUpOneLabelDown(changePasswordPage);
        addPageToBackButton(changePasswordPage);
    }

    private void myAccountPageAuditPageButton(){
        oneLabelUpOneLabelDown(auditPage);
        auditPage.initAuditTableData();
        addPageToBackButton(auditPage);
    }

    private void myAccountPageChangeEmailButton(){
        if(myAccountPage.validEmailAdress()){
            myAccountPage.updateEmailAdress();
            myAccountPage.getChangeEmailField().setText("");
            log.createAuditLog("CHANGED EMAIL ADRESS");
        }else{
            myAccountPage.getChangeEmailField().setText("");
        }
    }

    private void myAccountPageChangeUsernameButton(){
        if(myAccountPage.validUsername()){
            myAccountPage.updateUsername();
            myAccountPage.getChangeUsernameField().setText("");
            log.createAuditLog("CHANGED USERNAME");
        }else{
            myAccountPage.getChangeUsernameField().setText("");
        }
    }

    private void changePasswordPageChangePasswordButton(){
        if(changePasswordPage.validPassword()){
            changePasswordPage.updatePassword();
            changePasswordPage.getPasswordField().setText("");
            changePasswordPage.getConfirmPasswordField().setText("");
            log.createAuditLog("CHANGED PASSWORD");
            moveTwoLabelsDown(loginPage);
            backAndForwardList.clear();
            backAndForward.setSetListIterator(true);
        //    setListIterator = true;
        }else{
            changePasswordPage.getPasswordField().setText("");
            changePasswordPage.getConfirmPasswordField().setText("");
        }
    }

    private void flightPageCancelButton(){
        addFlightsPage.resetFields();
        oneLabelUpOneLabelDown(homePage);
        addPageToBackButton(homePage);
    }

    private void flightPageAddFlightButton(){
        if(addFlightsPage.valid()) {
            addFlightsPage.addFlight();
            addFlightsPage.resetFields();
            homePage.tableData();
            log.createAuditLog("ADDED A FLIGHT");
            oneLabelUpOneLabelDown(homePage);
            addPageToBackButton(homePage);
        }
    }

    private void closeProgram(){
        if (PersonController.getInstance().getLoggedPerson() != null) {
            log.createAuditLog("SHUTDOWN");
        }

        dispose();
        homePage.getService().shutdown();
        service.shutdown();
    }

    private void addPageToBackButton(JLabel page){
        backAndForwardList.add(page);
        backAndForward.setSetListIterator(true);
   //     setListIterator = true;
    }

    private void hatzInSpateHatzInFata(List<JLabel> list, String action){

        if(!list.isEmpty()){
            if(setListIterator){
                listIterator = list.listIterator(list.size()-1);
                setListIterator =false;
            }

            if(listIterator == list.listIterator(list.size()-1) ){
                forward = false;
            }

            switch(action){

                case "back":
                    if(loginPage.getY() !=0 && list.get(listIterator.previousIndex()).getY() == 0) { // <- anti-spam button
                        if (!back) {
                            if (listIterator.hasPrevious()) {
                                listIterator.previous();
                                back = true;
                            }
                        }
                    }

                    if(!(listIterator.nextIndex() >= list.size())){

                        if(loginPage.getY() !=0 && list.get(listIterator.nextIndex()).getY() == 0){ // <- anti-spam button
                            if(listIterator.previousIndex() == 0){
                                moveTwoLabelsDown(listIterator.previous());
                                list.clear();
                                setListIterator =true;
                            }

                            if(listIterator.hasPrevious()){
                                oneLabelUpOneLabelDown(listIterator.previous());
                                forward = true;
                            }
                        }
                    }
                    break;

                case "forward":

                    if(forward){
                        if(loginPage.getY() !=0 && list.get(listIterator.nextIndex()).getY() == 0) { //<- anti-spam button

                            if (listIterator.hasNext()) {
                                listIterator.next();
                                forward = false;
                            }
                        }
                    }

                    if(loginPage.getY() !=0 && list.get(listIterator.previousIndex()).getY() == 0){ // <- anti-spam button

                        if(listIterator.hasNext()){
                            oneLabelUpOneLabelDown(listIterator.next());
                            back = false;
                        }
                    }
                    break;
            }
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

    public void setBackAndForwardList() {
        this.backAndForwardList.clear();
    }

    public void setSetListIterator(boolean setListIterator) {
        this.setListIterator = setListIterator;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }

    public MyAccountPage getMyAccountPage() {
        return myAccountPage;
    }

    public void setMyAccountPage(MyAccountPage myAccountPage) {
        this.myAccountPage = myAccountPage;
    }


}
