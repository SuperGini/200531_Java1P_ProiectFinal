package view.labels;

import controller.AuditController;
import controller.PersonController;
import models.Audit;
import models.Person;
import view.panel.TransparentPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuditPage extends JLabel{

    private int width = 1125;
    private int height = 750;

    private JPanel transparentPanel;
    private JTable auditTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private List<Audit> audits;

    public AuditPage() {
        setBounds(0, 1100, width, height);
        initTransparentPanel();
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        initAuditTable();
    }


    private void initTransparentPanel(){
        transparentPanel = new TransparentPanel(100,175,900,420){
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        add(transparentPanel);
    }

    public void initAuditTable(){

        auditTable = new JTable(model);
        scrollPane = new JScrollPane(auditTable);
        scrollPane.setBounds(49,10,798,250);
        transparentPanel.add(scrollPane);

        auditTable.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,15 ));
        auditTable.setFont(new Font("Segoe UI", Font.BOLD, 12));
        auditTable.getTableHeader().setOpaque(false);
        auditTable.getTableHeader().setBackground(new Color(32,136,203));
        auditTable.getTableHeader().setForeground(new Color(255,255,255));
        auditTable.setSelectionBackground(new Color (232,57,95));
        auditTable.setRowHeight(25);
        auditTable.setShowVerticalLines(false);
        auditTable.getTableHeader().setReorderingAllowed(false);



    }


    public void initAuditTableData(){
        String username = null;
        Optional<Person> person = PersonController.getInstance().getLoggedPerson();
        if(person.isPresent()){
             username = person.get().getUsername();

        }

                //todo de vazut cum scot din baza da date direct reversed
        audits = AuditController.getInstance().showAuditLog(username)
                            .stream()
                            .sorted(Comparator.comparing(Audit::getId).reversed())
                            .collect(Collectors.toList());


        String [] columns ={ "Username", "Action", "Date and Time"};
        String [] row = new String [3];



        model.setRowCount(0);
        model.setColumnIdentifiers(columns);

        for(Audit audit: audits){
            row[0] = audit.getUsername();
            row[1] = audit.getAction();
            row[2] = audit.getDateAndTime();
            model.addRow(row);
        }

        for(int i = 0; i < 3;i++ ){
            auditTable.getColumnModel().getColumn(i).setMaxWidth(266);
            auditTable.getColumnModel().getColumn(i).setMinWidth(266);
        }
    }

    @Override
    public String toString() {
        return "AUDIT PAGE";
    }
}
