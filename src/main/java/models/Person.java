package models;

public class Person {

    private int id;
    private String username;
    private String emailAdress;
    private String password;


    public Person(int id, String username, String emailAdress,String password) {
        this.id = id;
        this.username = username;
        this.emailAdress = emailAdress;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }



}
