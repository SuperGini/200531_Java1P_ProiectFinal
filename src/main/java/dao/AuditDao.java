package dao;

import models.Audit;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuditDao {

    private Connection conection;

    private PreparedStatement createLog;
    private PreparedStatement lastUserAction;
    private PreparedStatement showAuditLog;

    private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    public AuditDao(Connection connection){
        this.conection = connection;

        try {
            createLog = connection.prepareStatement("INSERT INTO audit VALUES (null , ?, ?, ?, ?)");
            lastUserAction = connection.prepareStatement("SELECT * FROM audit WHERE username =? AND id = ( SELECT MAX(id) FROM audit )");
            showAuditLog = connection.prepareStatement("SELECT * FROM audit WHERE username =? ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean createLog(Audit audit){
        try {

            createLog.setString(1, audit.getUsername());
            createLog.setString(2, audit.getAction());
            createLog.setString(3, LocalDateTime.now().format(formatTime)); //todo bagi direct data caledaristica si gata
            createLog.setInt(4, audit.getPersonId());
            return createLog.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Audit> lastUserAction(String username){
        List<Audit> audits = new ArrayList<>();
        try {
            lastUserAction.setString(1, username);

            ResultSet rs = lastUserAction.executeQuery();
            if(rs.next()){
                Audit audit = new Audit.Builder()
                        .setUsername(rs.getString("username"))
                        .setAction(rs.getString("action"))
                        .setDate(rs.getString("date_time"))
                        .setPersonId(rs.getInt("person_id"))
                        .build();
                audits.add(audit);
            }

            return audits;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<Audit> showAuditLog(String username){
        List<Audit> audits = new ArrayList<>();

        try {
            showAuditLog.setString(1, username);
            ResultSet rs = showAuditLog.executeQuery();

            while(rs.next()){
                Audit audit = new Audit.Builder()
                                    .setUsername(rs.getString("username"))
                                    .setAction(rs.getString("action"))
                                    .setDate(rs.getString("date_time"))
                                    .setId(rs.getInt("id"))
                                    .build();
                audits.add(audit);
            }
            return audits;

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return Collections.emptyList();
    }
}
