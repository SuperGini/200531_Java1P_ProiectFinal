package dao;

import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PersonDao {

    private Connection connection;
    private PreparedStatement createStatement;
    private PreparedStatement findByUsername;
    private PreparedStatement findByEmailAdress;
    private PreparedStatement findByPassword;
    private PreparedStatement updateUsername;
    private PreparedStatement updateEmail;
    private PreparedStatement  updatePassword;


    public PersonDao(Connection connection){
        this.connection = connection;


        try {
            createStatement = connection.prepareStatement("INSERT INTO person VALUES (null, ?, ?,?)");
            findByUsername = connection.prepareStatement("SELECT * FROM person WHERE username = ?");
            findByEmailAdress = connection.prepareStatement("SELECT * FROM person WHERE email_adress = ?");
            findByPassword = connection.prepareStatement("SELECT * FROM person WHERE password = ?");
            updateUsername = connection.prepareStatement("UPDATE person SET username = ?  WHERE username = ? ");
            updateEmail = connection.prepareStatement("UPDATE person SET email_adress = ? WHERE email_adress = ?");
            updatePassword = connection.prepareStatement("UPDATE person SET password  = ? WHERE username = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean create (Person person){

        try {
            createStatement.setString(1, person.getUsername());
            createStatement.setString(2, person.getEmailAdress());
            createStatement.setString(3,person.getPassword());
            return createStatement.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Person> findByUsername(String username){

        try {

            findByUsername.setString(1, username);
            ResultSet rs = findByUsername.executeQuery();
            if(rs.next()){
                return Optional.of(new Person(
                                              rs.getInt("id"),
                                              rs.getString("username"),
                                              rs.getString("email_adress"),
                                              rs.getString("password")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Person> findByEmailAdress(String emailAdress){
        try {
            findByEmailAdress.setString(1,emailAdress);
            ResultSet rs = findByEmailAdress.executeQuery();
            if(rs.next()){
                return Optional.of(new Person(
                                              rs.getInt("id"),
                                              rs.getString("username"),
                                              rs.getString("email_adress"),
                                              rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Person> findByPassword(String password){
        try {
            findByPassword.setString(1, password);
            ResultSet rs = findByPassword.executeQuery();
            if(rs.next()){
                return Optional.of(new Person(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email_adress"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean updateUsername(String newUsername, String username){
        try {

            updateUsername.setString(1,newUsername);
            updateUsername.setString(2, username);
           return  updateUsername.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmailAdress(String newEmailAdress, String emailAdress){
        try {
            updateEmail.setString(1,newEmailAdress);
            updateEmail.setString(2, emailAdress);
            return updateEmail.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String newPassword, String username){

        try {
            updatePassword.setString(1, newPassword);
            updatePassword.setString(2, username);

            return updatePassword.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
