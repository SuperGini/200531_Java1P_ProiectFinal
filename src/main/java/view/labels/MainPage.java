package view.labels;

import controller.FlightController;
import models.Flight;
import view.buttons.RedButton;
import view.panel.TransparentPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainPage extends JLabel {

    private JPanel transparentPanel;
    private JTable flightTable;
    private JButton adaugaZbor;
    private JButton deleteFlight;
    private JLabel dateAndClockLabel;
    public DefaultTableModel model;

    private int width = 1125;
    private int height = 750;


    private MainPage(){
        this.setBounds(0,1100,width, height);
        initTransparentPanel ();


        initAdaugaZborButton();
        initDateAndClockLabel();
        model = new DefaultTableModel();
//        {
//           @Override
//           public boolean isCellEditable(int row, int column) {
//               return column == 5;
//           }
//       };


        TabelZboruri();
        tableData();
        setOpaque(false);
    }


    private void initTransparentPanel (){
        transparentPanel = new TransparentPanel(100,175,900,420){
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        add(transparentPanel);
    }

    private void initAdaugaZborButton(){
        adaugaZbor = new JButton("Adauga Zbor");
        adaugaZbor.setBounds(700,350,120,20);
        transparentPanel.add(adaugaZbor);
    }

    public void TabelZboruri(){

        flightTable = new JTable(model);
        JScrollPane table = new JScrollPane(flightTable);
        table.setBounds(50,10,800,250);
        transparentPanel.add(table);
        flightTable.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,15 ));
        flightTable.setFont(new Font("Segoe UI", Font.BOLD, 12));
        flightTable.getTableHeader().setOpaque(false);
        flightTable.getTableHeader().setBackground(new Color(32,136,203));
        flightTable.getTableHeader().setForeground(new Color(255,255,255));
        flightTable.setRowHeight(25);
        flightTable.setShowVerticalLines(false);
        flightTable.setSelectionBackground(new Color (232,57,95));
    }

    public void tableData(){
        java.util.List<Flight> flights = FlightController.getInstance().getAllFlights();
        model.setRowCount(0);

        model.setColumnIdentifiers(new Object[] {
                "id",
                "Sursa",
                "Destinatie",
                "Ora Plecare",
                "Ora Sosire",
                "Zile",
                "Pret",
                " ",

        });

        Object [] row = new Object [8];

        for (Flight flight : flights) {
            row[0] = flight.getId();
            row[1] = flight.getSursa();
            row[2] = flight.getDestinatie();
            row[3] = flight.getOraPlecare();
            row[4] = flight.getOraSosire();
            row[5] = flight.getZile();
            row[6] = flight.getPret();
            row[7] = "";
            model.addRow(row);
        }
        for(int i = 1; i < 7; i++){
            if(i <= 4 || i == 6){
                flightTable.getColumnModel().getColumn(i).setMaxWidth(100);
                flightTable.getColumnModel().getColumn(i).setMinWidth(100);
            }

            if(i == 5){
                flightTable.getColumnModel().getColumn(i).setMaxWidth(257);
                flightTable.getColumnModel().getColumn(i).setMinWidth(257);
            }
        }

        flightTable.removeColumn(flightTable.getColumnModel().getColumn(0));

        flightTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        flightTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField()));


    }




        //todo vezi ce e cu metoda de mai jos s aajusteze campurile
    private void updateRowHeights()
    {
        for (int row = 0; row < flightTable.getRowCount(); row++)
        {
            int rowHeight = flightTable.getRowHeight();

            for (int column = 0; column < flightTable.getColumnCount(); column++)
            {
                Component comp = flightTable.prepareRenderer(flightTable.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            flightTable.setRowHeight(row, rowHeight);
        }
    }

    private void initDateAndClockLabel(){
        dateAndClockLabel = new JLabel();
        dateAndClockLabel.setBounds(300,300, 175, 30);
        dateAndClockLabel.setFont(new Font("Serif",Font.ITALIC,20));
        transparentPanel.add(dateAndClockLabel);
        DateTimeFormatter timeFormatter = DateTimeFormatter
                .ofPattern("MM-dd-yyyy HH:mm:ss");

        Timer timer1 = new Timer(1000, e -> dateAndClockLabel.setText(LocalDateTime.now().format(timeFormatter)));
        timer1.start();
    }


    public JButton getAdaugaZbor() {
        return adaugaZbor;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getFlightTable() {
        return flightTable;
    }

    private static final class SingletonHolder{
        public static MainPage INSTANCE = new MainPage();
    }

    public static MainPage getInstance(){
        return SingletonHolder.INSTANCE;
    }
}


class ButtonRenderer extends JButton implements TableCellRenderer
{


    public ButtonRenderer() {
        
        this.setBounds(0, 0, 25,25);
        Image image = new ImageIcon("./src/main/resources/icons/red.png")
                .getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);
        this.setContentAreaFilled(false);
        this.setIcon(imageIcon);
        setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
                                                   boolean selected, boolean focused, int row, int col){
        return this;
    }
}


class ButtonEditor extends DefaultCellEditor{

    protected JButton btn;
    public ButtonEditor(JTextField txt) {
        super(txt);

        btn = new RedButton(0,0);
        btn.setOpaque(true);

        btn.addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(
                    null,"Delete Flight?","confirm box",JOptionPane.YES_NO_OPTION);
            MainPage mainPage = MainPage.getInstance();
            int row = mainPage.getFlightTable().getSelectedRow();
            int id = (Integer) mainPage.getFlightTable().getModel().getValueAt(row,0);
            if(id >=0){
                if(option == JOptionPane.YES_OPTION){
                    FlightController.getInstance().deleteFlight(id);
                    mainPage.tableData();
                }
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {
        return btn;
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}







