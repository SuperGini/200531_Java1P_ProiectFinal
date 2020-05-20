package controller;

import dao.FlightDao;
import models.Flight;

import java.util.List;

public class FlightController {

    private FlightDao flightDao;

    private FlightController(){
        flightDao = new FlightDao(ConnectionManager.getInstance().getConnection());
    }

    private static final class SingletonHolder{
        public static FlightController INSTANCE = new FlightController();
    }

    public static FlightController getInstance(){
       return  SingletonHolder.INSTANCE;
    }

    public boolean createFlight(Flight flight){
        return flightDao.createFlight(flight);

    }

    public boolean deleteFlight(int id){
        return flightDao.deleteFlight(id);
    }

    public List<Flight> getFlightList (String sursa){
        return flightDao.getFlightList(sursa);
    }

    public List<Flight> getAllFlights(){
        return flightDao.getAllFlights();
    }

    public FlightDao getFlightDao() {
        return flightDao;
    }


}
