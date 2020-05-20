package dao;

import models.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightDao {


    private Connection connection;

    private PreparedStatement createFlight;
    private PreparedStatement deleteFlight;
    private PreparedStatement findFlight;
    private PreparedStatement findAllFlights;


    public FlightDao(Connection connection) {
        this.connection = connection;

        try {
            createFlight = connection.prepareStatement("INSERT INTO flights VALUES (null,?,?,?,?,?,?)");
            deleteFlight = connection.prepareStatement("DELETE FROM flights WHERE id = ? ");
            findFlight = connection.prepareStatement("SELECT * FROM flights WHERE sursa = ?");
            findAllFlights = connection.prepareStatement("SELECT * FROM flights");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createFlight(Flight flight) {
        try {
            createFlight.setString(1, flight.getSursa());
            createFlight.setString(2, flight.getDestinatie());
            createFlight.setString(3, flight.getOraPlecare());
            createFlight.setString(4, flight.getOraSosire());
            createFlight.setString(5, flight.getZile());
            createFlight.setDouble(6, flight.getPret());
            return createFlight.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Flight> getFlightList(String sursa) {
        List<Flight> flights = new ArrayList<>();
        try {
            findFlight.setString(1, sursa);

            ResultSet rs = findFlight.executeQuery();

            while (rs.next()) {
                Flight flight = new Flight.Builder()
                        .setSursa(rs.getString("sursa"))
                        .setDestinatie(rs.getString("destinatie"))
                        .build();
                flights.add(flight);
            }
            return flights;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Flight> getAllFlights(){
        List<Flight> flights = new ArrayList<>();

        try {
           ResultSet rs = findAllFlights.executeQuery();

            while(rs.next()){
                Flight flight = new Flight.Builder()
                        .setId(rs.getInt("id"))
                        .setSursa(rs.getString("sursa"))
                        .setDestinatie(rs.getString("destinatie"))
                        .setOraPlecare(rs.getString("oraPlecare"))
                        .setOraSosire(rs.getString("oraSosire"))
                        .setZile(rs.getString("zile"))
                        .setPret(rs.getDouble("pret"))
                        .build();
                flights.add(flight);
            }
            return flights;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public boolean deleteFlight(int id){

        try {
            deleteFlight.setInt(1,id);
            return deleteFlight.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}