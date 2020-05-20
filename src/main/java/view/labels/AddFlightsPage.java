package view.labels;

import controller.FlightController;
import models.Flight;
import view.panel.TransparentPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddFlightsPage extends JLabel {
    private JPanel transparentPanel;
    private JLabel sursaLabel;
    private JLabel destinatieLabel;
    private JLabel oraPlecareLabel;
    private JLabel durataLabel;
    private JLabel pretLabel;
    private JLabel ziLabel;
    private JTextField sursaField;
    private JTextField destinatieField;
    private JTextField oraPlecareField;
    private JTextField durataField;
    private JTextField pretField;
    private JButton addFlightButton;
    private JButton anulateButton;
    private JCheckBox checkBoxButton;
    private String[] days = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Samabata", "Duminica"};
    private MainPage mainPage = MainPage.getInstance();

    private List<JCheckBox> buttons;
    private List<JLabel> labels;


    private int width = 1125;
    private int height = 750;

    public AddFlightsPage() {
        this.setBounds(0, 1100, width, height);
        initTransparentPanel();
        initAdaugaZborButton();
        initAnuleazaButton();
        initSursaLabel();
        initSursaField();
        initDestinatieLabel();
        initDestiantieField();
        initOraPlecareLabel();
        initOraPlecareField();
        initDurataLabel();
        initDurataField();
        initPretLabel();
        initPretFiled();
        initCheckBoxLabel();
        initCheckbox();

    }

    private void initTransparentPanel() {
        transparentPanel = new TransparentPanel(100,175,900,420){
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        add(transparentPanel);
    }

    private void initAdaugaZborButton() {
        addFlightButton = new JButton("Adauga zbor");
        addFlightButton.setBounds(195, 350, 220, 30);
        transparentPanel.add(addFlightButton);
//        addFlightButton.addActionListener(e -> {
//            if (valid()) {
//                addFlight();
//                mainPage.getModel().setRowCount(0);
//                mainPage.showFlights();
//            }
//        });
    }

    private void initAnuleazaButton() {
        anulateButton = new JButton("Anuleaza");
        anulateButton.setBounds(455, 350, 220, 30);
        transparentPanel.add(anulateButton);
    }

    private void initSursaLabel() {
        sursaLabel = new JLabel("Sursa");
        sursaLabel.setBounds(20, 40, 120, 25);
        transparentPanel.add(sursaLabel);
    }

    private void initSursaField() {
        sursaField = new JTextField();
        sursaField.setBounds(20, 60, 200, 25);
        transparentPanel.add(sursaField);
    }

    private void initDestinatieLabel() {
        destinatieLabel = new JLabel("Destiantie");
        destinatieLabel.setBounds(300, 40, 120, 25);
        transparentPanel.add(destinatieLabel);
    }

    private void initDestiantieField() {
        destinatieField = new JTextField();
        destinatieField.setBounds(300, 60, 200, 25);
        transparentPanel.add(destinatieField);
    }

    private void initOraPlecareLabel() {
        oraPlecareLabel = new JLabel("Ora plecare");
        oraPlecareLabel.setBounds(20, 90, 120, 25);
        transparentPanel.add(oraPlecareLabel);
    }

    private void initOraPlecareField() {
        oraPlecareField = new JTextField();
        oraPlecareField.setBounds(20, 110, 200, 25);
        transparentPanel.add(oraPlecareField);
    }

    private void initDurataLabel() {
        durataLabel = new JLabel("Durata");
        durataLabel.setBounds(300, 90, 120, 25);
        transparentPanel.add(durataLabel);
    }

    private void initDurataField() {
        durataField = new JTextField();
        durataField.setBounds(300, 110, 200, 25);
        transparentPanel.add(durataField);
    }

    private void initPretLabel() {
        pretLabel = new JLabel("Pret");
        pretLabel.setBounds(170, 230, 100, 25);
        transparentPanel.add(pretLabel);
    }

    private void initPretFiled() {
        pretField = new JTextField();
        pretField.setBounds(210, 230, 100, 25);
        transparentPanel.add(pretField);

    }


    private void initCheckbox() {
        buttons = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String zi = labels.get(i).getText();
            checkBoxButton = new JCheckBox(zi);
            checkBoxButton.setBounds(20, 150 + (i * 25), 20, 20);
            transparentPanel.add(checkBoxButton);
            buttons.add(checkBoxButton);
        }
    }

    private void initCheckBoxLabel() {
        labels = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ziLabel = new JLabel(days[i]);
            ziLabel.setBounds(50, 140 + (i * 25), 70, 50);
            transparentPanel.add(ziLabel);
            labels.add(ziLabel);

        }
    }

    public boolean valid() {
        if (sursaField.getText().length() < 3) {
            showMessage("Sursa trebuie sa aiba mai mult de 2 caractere");
            return false;
        }

        if (destinatieField.getText().length() < 3) {
            showMessage("destinatia trebuie sa aiba mai mult de 2 caractere");
            return false;
        }

        if (sursaField.getText().equals(destinatieField.getText())) {
            showMessage("Sursa si destinatia trebuie sa fie diferite");
            return false;
        }

        if (validSursaAndDestiantie()) {
            showMessage("Aceast zbor exista");
            return false;
        }


        if (timeValidation(oraPlecareField.getText())) {
            showMessage("Format ora plecare introdus gresit. Format valid HH:mm");
            return false;
        }

        if (timeValidation(durataField.getText())) {
            showMessage("Format durata introdus gresit. Foramt valid HH:mm");
            return false;
        }
        if (boxesChecked()) {
            showMessage("Selecteaza cel putin o zi");
            return false;
        }

        try {
            if (Double.parseDouble(pretField.getText()) <= 0) {
                showMessage("Pretul nu poate sa fie zero sau mai mic ca zero");
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showMessage("Pretul nu are formatul corect");
            return false;
        }
        return true;
    }

    //todo de vazut cum selectez sursa si destinatie din baza ca sa nu mai trebuiasca sa fac stream
    public boolean validSursaAndDestiantie() {

        List<Flight> flights = FlightController.getInstance().getFlightList(sursaField.getText())
                .stream()
                .filter(s -> s.getDestinatie().equals(destinatieField.getText()))
                .collect(Collectors.toList());

        return !flights.isEmpty();
    }

    private boolean boxesChecked() {
        List<String> string = buttons.stream()
                .filter(AbstractButton::isSelected)
                .map(AbstractButton::getActionCommand)
                .collect(Collectors.toList());

        return string.isEmpty();
    }


    private boolean timeValidation(String hour) {
        String hourRegex = "([0-1][0-9]|2[0-3]):[0-5][0-9]";

        Pattern pattern = Pattern.compile(hourRegex);
        if (hour == null) {
            return true;
        }

        return !pattern.matcher(hour).matches();
    }

    private String getOraSosire(String oraPlecare, String durata){

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time1 = LocalTime.parse(oraPlecare, format);
        LocalTime time2 = LocalTime.parse(durata, format);

        int hours = time2.getHour();
        int minutes = time2.getMinute();
        LocalTime time3 = time1.plusHours(hours).plusMinutes(minutes);

       return time3.format(format);
    }

    public void addFlight() {
        String sursa = sursaField.getText();
        String destinatie = destinatieField.getText();
        String oraPlecare = oraPlecareField.getText();
        String oraSosire = getOraSosire(oraPlecareField.getText(), durataField.getText());
        double pret = Double.parseDouble(pretField.getText());

        String zile = buttons.stream()
                .filter(AbstractButton::isSelected)
                .map(AbstractButton::getActionCommand)
                .collect(Collectors.joining(","));

        Flight flight = new Flight.Builder()
                .setSursa(sursa)
                .setDestinatie(destinatie)
                .setOraPlecare(oraPlecare)
                .setOraSosire(oraSosire)
                .setZile(zile)
                .setPret(pret)
                .build();

        boolean flightAdded = FlightController.getInstance().createFlight(flight);
        if (flightAdded) {

            JOptionPane.showMessageDialog(null, "BRAVO BOSSSSSS");

        } else {
            JOptionPane.showMessageDialog(null, "nu merge");
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getAnulateButton() {
        return anulateButton;
    }

    public JButton getAddFlightButton() {
        return addFlightButton;
    }


}
