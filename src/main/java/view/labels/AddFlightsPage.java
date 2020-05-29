package view.labels;

import controller.FlightController;
import models.Flight;
import view.panel.TransparentPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField departureHourField;
    private JTextField durationField;
    private JTextField priceField;
    private JButton addFlightButton;
    private JButton cancelButton;
    private JCheckBox checkBoxButton;
    private String[] days = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Samabata", "Duminica"};


    private Map<Integer, JCheckBox> buttons;
    private List<JLabel> labels;



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
        sourceField = new JTextField();
        sourceField.setBounds(20, 60, 200, 25);
        transparentPanel.add(sourceField);
    }

    private void initDestinatieLabel() {
        destinationLabel = new JLabel("Destination");
        destinationLabel.setBounds(300, 40, 120, 25);
        transparentPanel.add(destinationLabel);
    }

    private void initDestiantieField() {
        destinationField = new JTextField();
        destinationField.setBounds(300, 60, 200, 25);
        transparentPanel.add(destinationField);
    }

    private void initOraPlecareLabel() {
        departureHourLabel = new JLabel("Departure hour");
        departureHourLabel.setBounds(20, 90, 120, 25);
        transparentPanel.add(departureHourLabel);
    }

    private void initOraPlecareField() {
        departureHourField = new JTextField();
        departureHourField.setBounds(20, 110, 200, 25);
        transparentPanel.add(departureHourField);
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
        buttons = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            String day = days[i];
            checkBoxButton = new JCheckBox(day);
            checkBoxButton.setOpaque(false);
            checkBoxButton.setBounds(20, 150 + (i * 25), 20, 20);
            transparentPanel.add(checkBoxButton);
            buttons.put(i, checkBoxButton);
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
        String  image = "./src/main/resources/icons/worldmap1.png";
        worldIcon = new SetSizeAndImage(550,170,305,159, image);
        transparentPanel.add(worldIcon);
    }

    public boolean valid() {
        if (sourceField.getText().length() < 3) {
            showMessage("Source must have minimum three characters");
            return false;
        }

        if (destinationField.getText().length() < 3) {
            showMessage("Destination must have minimum three characters");
            return false;
        }

        if (sourceField.getText().equals(destinationField.getText())) {
            showMessage("Source and destination must be different");
            return false;
        }

        if (validSursaAndDestiantie()) {
            showMessage("This Flight allready exists");
            return false;
        }


        if (timeValidation(departureHourField.getText())) {
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

        List<Flight> flights = FlightController.getInstance().getFlightList(sourceField.getText())
                .stream()
                .filter(s -> s.getDestinatie().equals(destinationField.getText()))
                .collect(Collectors.toList());

        return !flights.isEmpty();
    }

    private boolean boxesChecked() {
        List<String> string = buttons.entrySet()
                .stream()
                .filter(e -> e.getValue().isSelected())
                .map(e -> e.getValue().getActionCommand())
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
        String sursa = sourceField.getText();
        String destinatie = destinationField.getText();
        String oraPlecare = departureHourField.getText();
        String oraSosire = getOraSosire(departureHourField.getText(), durationField.getText());
        double pret = Double.parseDouble(priceField.getText());

        String zile = buttons.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .filter(e -> e.getValue().isSelected())
                .map( e -> e.getValue().getActionCommand())
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

    public void resetFields(){
        for(Integer button : buttons.keySet()){  //resets the selected checkbox
            if(buttons.get(button).isSelected()){
                buttons.get(button).setSelected(false);
            }
        }
        sourceField.setText("");
        destinationField.setText("");
        departureHourField.setText("");
        durationField.setText("");
        priceField.setText("");
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getAddFlightButton() {
        return addFlightButton;
    }

    @Override
    public String toString() {
        return "ADD FLIGHT PAGE";
    }
}
