package controller;

import dao.PersonDao;
import models.Person;

import java.util.Optional;

public class PersonController {

    private PersonDao personDao;
    private Optional<Person> loggedPerson;

    private PersonController() {
        personDao = new PersonDao(ConnectionManager.getInstance().getConnection());
    }

    private static final class SingletonHolder{
        public static final PersonController INSTANCE = new PersonController();
    }

    public static PersonController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public boolean create(Person person){
        Optional<Person> personOptional1 = personDao.findByUsername(person.getUsername());
        Optional<Person> personOptional2 = personDao.findByEmailAdress(person.getEmailAdress());

        if(personOptional1.isEmpty() && personOptional2.isEmpty()){
            return personDao.create(person);
        }
        return false;
    }

    public Optional<Person> findByUsername(String username){
         return  personDao.findByUsername(username);
    }

    public Optional<Person> findByEmaiAdress(String emailAdress){
        return personDao.findByEmailAdress(emailAdress);
    }



    public boolean updateUsername(String newUsername, String username){
        return personDao.updateUsername(newUsername,username);
    }

    public Optional<Person> loginUsername(String username, String password){
        Optional<Person> person = personDao.findByUsername(username);
        if(person.isPresent()){
            if(person.get().getPassword().equals(password)){
                setLoggedPerson(person);
                return person;
            }
        }
        return Optional.empty();
    }

    public Optional<Person> loginEmailAdress(String email, String password){
        Optional<Person> person = personDao.findByEmailAdress(email);
        if(person.isPresent()){
            if(person.get().getPassword().equals(password)){
                setLoggedPerson(person);
                return person;
            }
        }
        return Optional.empty();
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public boolean updateEmailAdress(String newEmailAdress, String emailAdress){
        return personDao.updateEmailAdress(newEmailAdress, emailAdress);
    }

    public boolean updatePassword(String newPassword, String username){
        Optional<Person> person = personDao.findByUsername(username);
        if(person.isPresent()){
            return personDao.updatePassword(newPassword, username);
        }
        return false;
    }

    public Optional<Person> getLoggedPerson() {
        return loggedPerson;
    }

    public void setLoggedPerson(Optional<Person> loggedPerson) {
        this.loggedPerson = loggedPerson;
    }
}
