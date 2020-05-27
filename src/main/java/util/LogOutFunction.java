package util;

import controller.AuditController;
import controller.PersonController;
import models.Audit;
import models.Person;
import view.frames.CentralFrame;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class LogOutFunction {

    private AuditController log;
    private CentralFrame centralFrame;
    private Timer logoutTimer;

    public LogOutFunction(){
        log = AuditController.getInstance();
        centralFrame = CentralFrame.getInstance();
    }

    public Timer getLogOutTimer(){
        int chechInterval =60 * 1000;
        logoutTimer = new Timer(chechInterval, e -> startCountDown());
        return logoutTimer;
    }

    private void startCountDown(){
        Optional<Person> person = PersonController.getInstance().getLoggedPerson();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        if(person.isPresent()) {
            List<Audit> lastUserAction = log.lastUserAction(person.get().getUsername());

            String lastAction = lastUserAction.get(0).getDateAndTime();

            LocalDateTime t1 = LocalDateTime.parse(lastAction, format);
            LocalDateTime t2 = LocalDateTime.now();

            long elapsedMinutes = Duration.between(t1, t2).toMinutes();
            if (elapsedMinutes == 15) {
                logoutTimer.stop();
                centralFrame. moveTwoLabelsDown(centralFrame.getLoginPage());
                centralFrame.setSetListIterator(true);
                centralFrame.setLabelbackButton1();

            }
        }
    }
}
