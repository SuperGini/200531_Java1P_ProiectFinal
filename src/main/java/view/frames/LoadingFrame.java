package view.frames;

import AppPackage.AnimationClass;
import media.SoundPlay;
import view.buttons.MiniButtons;
import view.labels.SetSizeAndImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadingFrame extends JFrame{

    private JButton startButton;
    private JButton closeButton;
    private JButton loginButton;
    private JButton registerButton;
    private MiniButtons xButton;
    private JPanel panel;

    private SetSizeAndImage backgroundLabel2;
    private SetSizeAndImage backgroundLabel1;
    private JLabel loadingLabel;
    private ImageIcon greenImage;
    private ImageIcon redImage;
    private JProgressBar loadingBar;
    private int width = 584;
    private int height = 250;
    private int posX = 0, posY =0;
    private int i,j;

    private AnimationClass slideEfect = new AnimationClass();
    private SoundPlay soundPlay;

    public LoadingFrame(){
        initFrame();
        initBackground2();
        initBackground1();
        initStartButton();
        initCloseButton();
        initLoginButton();
        initRegisterButton();
        initExitButton();
        initLoadingBar();
        initLoadingLable();
        mouseListener();
        setLayout(null);
        soundPlay = new SoundPlay();
        setVisible(true);
    }


    private void initFrame(){
        setSize(width,height);
        setLocationRelativeTo(null);
        setUndecorated(true);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(width, height);
        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initBackground1(){
        String filePath = "./src/main/resources/imagesGif/startPage.gif";
        backgroundLabel1 = new SetSizeAndImage(584,250,filePath);
        panel.add(backgroundLabel1);
    }

    private void initBackground2(){
        String filePath = "./src/main/resources/imagesGif/loadingPageBackground.gif";
        backgroundLabel2 = new SetSizeAndImage(584,250,filePath);
        panel.add(backgroundLabel2);
    }

    private void initStartButton(){
        Image scaledImage1 = new ImageIcon("./src/main/resources/icons/start.png")
                .getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        greenImage = new ImageIcon(scaledImage1);

        startButton = new JButton();
        startButton.setBounds(257,190,30,30);
        startButton.setContentAreaFilled(false);
      //  startButton.setBorderPainted(false);
        backgroundLabel2.add(startButton);
        startButton.setIcon(greenImage);

        startButton.addActionListener(e-> startSoundLoadingBarAndLoadingLabel());
    }

    private void initCloseButton(){
        Image scaledImage2 = new ImageIcon("./src/main/resources/icons/close.png")
                .getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        redImage = new ImageIcon(scaledImage2);

        closeButton = new JButton();
        closeButton.setIcon(redImage);
        closeButton.setBounds(297,190,30,30);
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);
      //  closeButton.setBorderPainted(false);
        backgroundLabel2.add(closeButton);

        closeButton.addActionListener(e-> exitProgram());
    }

    private void initLoadingBar(){
        loadingBar = new JProgressBar(0,1000);
        loadingBar.setBounds(0,235,584,15);
        loadingBar.setBorderPainted(false);
        loadingBar.setOpaque(false);

        loadingBar.setForeground(Color.green);
        backgroundLabel2.add(loadingBar);
    }

    private void initLoadingLable(){
        loadingLabel = new JLabel("loading");
        loadingLabel.setBounds(50,150,100,40);
        loadingLabel.setForeground(Color.green);
        loadingLabel.setVisible(false);
        backgroundLabel2.add(loadingLabel);
    }

    private void initLoginButton(){
        loginButton = new JButton("login");
        loginButton.setBounds(-110,210,100,20);
      //  loginButton.setForeground(Color.WHITE);
       // loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      //  loginButton.setBackground(new Color(255,255,255,30));
        backgroundLabel1.add(loginButton);

        loginButton.addActionListener(e->{
        //    CentralFrame.getInstance();
            timer3.start();
            dispose();
        });
    }

    private void initRegisterButton() {
        registerButton = new JButton("register");

        registerButton.setBounds(594, 210, 100, 20);
//        registerButton.setForeground(Color.WHITE);
//        registerButton.setBackground(new Color(255, 255, 255, 30));
        backgroundLabel1.add(registerButton);

        registerButton.addActionListener(e ->{
         //   CentralFrame.getInstance();
            timer4.start();
            dispose();
        });
    }

    private void initExitButton(){
        String filepath = "./src/main/resources/icons/miniGifs/3x.gif";
        xButton = new MiniButtons(600,0, filepath);
        backgroundLabel1.add(xButton);
        xButton.addActionListener(e-> dispose());

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

    Timer timerLoadingBar = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(i == 1000){
                    timerLoadingBar.stop();
                    timerLoadingLabel.stop();
                    loadingLabel.setText("starting");
                    soundPlay.getSound().stop();
                    slideEfect.jLabelYUp(0,-260,35,2,backgroundLabel2);
                }
                if(i == 500){

                    slideEfect.jButtonXRight(297,600,17,1,closeButton);
                    slideEfect.jButtonXLeft(257,-40,17,1,startButton);
                }
                i++;
                loadingBar.setValue(i);
            }
        });

    Timer timerLoadingLabel = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!loadingLabel.isVisible()){
                loadingLabel.setVisible(true);
            }else{
                loadingLabel.setVisible(false);
            }
        }
    });

    Timer timerLoginRegisterButton = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(j == 1200){
                slideEfect.jButtonXRight(-110,187,10,1,loginButton);
                slideEfect.jButtonXLeft(594,297,10,1,registerButton);
            }

            if(j == 1500){
                slideEfect.jButtonXLeft(600,556,10,1,xButton);
                timerLoginRegisterButton.stop();
            }
           j++;
        }
    });

    private void startSoundLoadingBarAndLoadingLabel(){
        timerLoadingBar.start();
        timerLoadingLabel.start();
        timerLoginRegisterButton.start();
        soundPlay.getSound().start();
    }

    private void exitProgram(){
        dispose();
        timerLoadingBar.stop();
        timerLoadingLabel.stop();
        timerLoginRegisterButton.stop();
        soundPlay.getSound().close();
    }

    Timer timer3 = new Timer(0, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            slideEfect.jLabelYDown(-1100,0,10,4, CentralFrame.getInstance().getLoginPage());
            timer3.stop();
        }
    });

    Timer timer4 = new Timer(0, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            slideEfect.jLabelYDown(-1100,0,10,4, CentralFrame.getInstance().getRegisterPage());
            timer4.stop();
        }
    });
}
