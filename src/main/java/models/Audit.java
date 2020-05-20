package models;

public class Audit {
    private int id;
    private String username;
    private String action;
    private String dateAndTime;
    private int personId;


    public Audit(){
    }

    public static class Builder{
        private Audit audit = new Audit();

        public Builder setId(int id){
            audit.id = id;
            return this;
        }

        public Builder setAction(String action){
            audit.action = action;
            return this;
        }

        public Builder setDate(String date){
            audit.dateAndTime = date;
            return this;
        }

        public Builder setUsername(String username){
            audit.username = username;
            return this;
        }

        public Builder setPersonId(int personId){
            audit.personId = personId;
            return this;
        }

        public Audit build(){
            return audit;
        }
    }

    public int getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public int getPersonId() {
        return personId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", action='" + action + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", personId=" + personId +
                '}';
    }
}
