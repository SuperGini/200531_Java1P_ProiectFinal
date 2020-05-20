package controller;

import dao.AuditDao;
import models.Audit;
import models.Person;

import java.util.List;
import java.util.Optional;

public class AuditController {

    private AuditDao auditDao;
    

    private AuditController(){
        auditDao = new AuditDao(ConnectionManager.getInstance().getConnection());
    }


    private static class SingletonHolder{
        public static AuditController INSTANCE = new AuditController();
    }

    public static AuditController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public boolean createLog(Audit audit){
        return auditDao.createLog(audit);
    }

    public boolean createAuditLog(String actions){
        Optional<Person> logedPerson = PersonController.getInstance().getLoggedPerson();
        if (logedPerson.isPresent()) {

            String username = logedPerson.get().getUsername();
            int personId = logedPerson.get().getId();

            Audit audit = new Audit.Builder()
                    .setUsername(username)
                    .setAction(actions)
                    .setPersonId(personId)
                    .build();
            auditDao.createLog(audit);
        }
        return false;
    }

    public List<Audit> lastUserAction(String username){
        return auditDao.lastUserAction(username);
    }

    public List<Audit> showAuditLog(String username) {
        return auditDao.showAuditLog(username);
    }
}
