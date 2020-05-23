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
    private JLabel surceLabel;
    private JLabel destinationLabel;
    private JLabel departureHourLabel;
    private JLabel durationLabel;
    private JLabel priceLabel;
    private JLabel dayLabel;
    private JLabel worldIcon;
    private JTextField sursaField;
    private JTextField destinatieField;
    private JTextField oraPlecareField;
    private JTextField durationField;
    private JTextField priceField;
    private JButton addFlightButton;
    private JButton cancelButton;
    private JCheckBox checkBoxButton;
    private String[] days = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Samabata", "Duminica"};


    private List<JCheckBox> buttons;
    private List<JLabel> labels;
    private List<JButton> miniButtons;


    private int width = 1125;
    private int height = 750;

    public AddFlightsPage() {
        this.setBounds(0, 1100, width, height);
        initTransparentPanel();
        iniAddFlightButton();
        initCancelButton();
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
        initWorldIcon();

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

    private void iniAddFlightButton() {
        addFlightButton = new JButton("Add Flight");
        addFlightButton.setBounds(195, 350, 220, 30);
        addFlightButton.setBackground(Color.CYAN);
        transparentPanel.add(addFlightButton);
    }

    private void initCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(455, 350, 220, 30);
        cancelButton.setBackground(Color.CYAN);
        transparentPanel.add(cancelButton);
    }

    private void initSursaLabel() {
        surceLabel = new JLabel("Source");
        surceLabel.setBounds(20, 40, 120, 25);
        transparentPanel.add(surceLabel);
    }

    private void initSursaField() {
        sursaField = new JTextField();
        sursaField.setBounds(20, 60, 200, 25);
        transparentPanel.add(sursaField);
    }

    private void initDestinatieLabel() {
        destinationLabel = new JLabel("Destination");
        destinationLabel.setBounds(300, 40, 120, 25);
        transparentPanel.add(destinationLabel);
    }

    private void initDestiantieField() {
        destinatieField = new JTextField();
        destinatieField.setBounds(300, 60, 200, 25);
        transparentPanel.add(destinatieField);
    }

    private void initOraPlecareLabel() {
        departureHourLabel = new JLabel("Departure hour");
        departureHourLabel.setBounds(20, 90, 120, 25);
        transparentPanel.add(departureHourLabel);
    }

    private void initOraPlecareField() {
        oraPlecareField = new JTextField();
        oraPlecareField.setBounds(20, 110, 200, 25);
        transparentPanel.add(oraPlecareField);
    }

    private void initDurataLabel() {
        durationLabel = new JLabel("Duration");
        durationLabel.setBounds(300, 90, 120, 25);
        transparentPanel.add(durationLabel);
    }

    private void initDurataField() {
        durationField = new JTextField();
        durationField.setBounds(300, 110, 200, 25);
        transparentPanel.add(durationField);
    }

    private void initPretLabel() {
        priceLabel = new JLabel("Price");
        priceLabel.setBounds(170, 230, 100, 25);
        transparentPanel.add(priceLabel);
    }

    private void initPretFiled() {
        priceField = new JTextField();
        priceField.setBounds(210, 230, 100, 25);
        transparentPanel.add(priceField);

    }


    private void initCheckbox() {
        buttons = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String day = days[i];
            checkBoxButton = new JCheckBox(day);
            checkBoxButton.setOpaque(false);
            checkBoxButton.setBounds(20, 150 + (i * 25), 20, 20);
            transparentPanel.add(checkBoxButton);
            buttons.add(checkBoxButton);
        }
    }

    private void initCheckBoxLabel() {
        labels = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dayLabel = new JLabel(days[i]);
            dayLabel.setBounds(50, 140 + (i * 25), 70, 50);
            transparentPanel.add(dayLabel);
            labels.add(dayLabel);
        }
    }

    private void initWorldIcon(){
        worldIcon = new JLabel();
        String  gif = "./src/main/resources/icons/worldmap1.png";
        worldIcon.setBounds(550,170,305,159);
        ImageIcon imageIcon = new ImageIcon(gif);
        worldIcon.setIcon(imageIcon);
        transparentPanel.add(worldIcon);
    }

    public boolean valid() {
        if (sursaField.getText().length() < 3) {
            showMessage("Source must have minimum three characters");
            return false;
        }

        if (destinatieField.getText().length() < 3) {
            showMessage("Destination must have minimum three characters");
            return false;
        }

        if (sursaField.getText().equals(destinatieField.getText())) {
            showMessage("Source and destination must be different");
            return false;
        }

        if (validSursaAndDestiantie()) {
            showMessage("This Flight allready exists");
            return false;
        }


        if (timeValidation(oraPlecareField.getText())) {
            showMessage("Departure time invalid format. Valid format HH:mm");
            return false;
        }

        if (timeValidation(durationField.getText())) {
            showMessage("Duration time invalid format. Valid format HH:mm");
            return false;
        }
        if (boxesChecked()) {
            showMessage("Select atleast one day");
            return false;
        }

        try {
            if (Double.parseDouble(priceField.getText()) <= 0) {
                showMessage("The price is zero or lower");
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showMessage("Wrong price");
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
        String oraSosire = getOraSosire(oraPlecareField.getText(), durationField.getText());
        double pret = Double.parseDouble(priceField.getText());

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

            JOptionPane.showMessageDialog(null, "Flight added");

        } else {
            JOptionPane.showMessageDialog(null, "Can't add flight");
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getAddFlightButton() {
        return addFlightButton;
    }
}
