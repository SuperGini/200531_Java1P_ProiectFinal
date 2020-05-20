package models;

public class Flight {

    private int id;
    private String sursa;
    private String destinatie;
    private String oraPlecare;
    private String oraSosire;
    private String zile;
    private double pret;

    public Flight(){
    }

    public static class Builder{
        private Flight flight = new Flight();

        public Builder setId(int id){
            flight.id = id;
            return this;
        }

        public Builder setSursa(String sursa){
            flight.sursa =sursa;
            return this;
        }

        public Builder setDestinatie(String destiantie){
            flight.destinatie = destiantie;
            return this;
        }

        public Builder setOraPlecare(String oraPlecare){
            flight.oraPlecare = oraPlecare;
            return this;
        }

        public Builder setOraSosire(String oraSosire){
            flight.oraSosire = oraSosire;
            return this;
        }

        public Builder setZile(String zile){
            flight.zile = zile;
            return this;
        }

        public Builder setPret(double pret){
            flight.pret = pret;
            return this;
        }

        public Flight build(){
            return flight;
        }
    }

    public int getId() {
        return id;
    }

    public String getSursa() {
        return sursa;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public String getOraSosire() {
        return oraSosire;
    }

    public String getZile() {
        return zile;
    }

    public double getPret() {
        return pret;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", sursa='" + sursa + '\'' +
                ", destinatie='" + destinatie + '\'' +
                ", oraPlecare='" + oraPlecare + '\'' +
                ", oraSosire='" + oraSosire + '\'' +
                ", zile='" + zile + '\'' +
                ", pret=" + pret +
                '}';
    }
}
