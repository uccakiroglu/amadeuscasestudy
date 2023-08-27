package net.cagatay.cakiroglu.amadeus_case_study.service;


import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Flight;
import net.cagatay.cakiroglu.amadeus_case_study.repositories.AirportRepository;
import net.cagatay.cakiroglu.amadeus_case_study.repositories.FlightRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class FlightServiceImpl implements FlightService{
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    @Transactional
    public Flight saveFlight(Flight flight) {
        Airport departureAirport = airportRepository.findByLocation(flight.getDepartureFrom().getLocation());
        Airport arrivalAirport = airportRepository.findByLocation(flight.getArrivalTo().getLocation());

        //Throw an error for nonexistent airport parameters
        if (Objects.isNull(departureAirport) || Objects.isNull(arrivalAirport)) {
            throw new IllegalArgumentException("Departure or arrival airport not found in the database");
        }
        else if (checkIfDatesValid(flight.getDepartureDate(), flight.getReturnDate()) && checkIfAirportsValid(departureAirport, arrivalAirport)){
            //Add return flight as a one-way flight to the database if there is one
            flight.setDepartureFrom(departureAirport);
            flight.setArrivalTo(arrivalAirport);
            if(Objects.nonNull(flight.getReturnDate())){
                create1WFlights(flight, arrivalAirport, departureAirport);
            }
        }
        return flightRepository.save(flight);
    }

    //READ
    //BY ID
    @Override
    public Flight fetchFlight(Long id){
        try{
            Flight flight = flightRepository.findById(id).get();
            return flight;
        }catch(Exception e){
            throw new IllegalArgumentException("No flight with the given ID found");
        }
    }

    //BY QUERY
    @Override
    public List<Flight> fetchFlights(Airport departureFrom, Airport arrivalTo, String departureDate, Optional<String> arrivalDate){
        List<Flight> flights = flightRepository.findByDepartureFromAndArrivalToAndDepartureDateAndReturnDate(departureFrom, arrivalTo, departureDate, arrivalDate);
        if(flights.size() == 0){
            throw new IllegalArgumentException("No flights with the given queries found");
        }
        return flights;
    }

    //UPDATE
    @Override
    public Flight updateFlight(Flight flight, Long flightId){
        Flight flightDB = flightRepository.findById(flightId).get();
        if (Objects.nonNull(flight.getArrivalTo()) && checkIfAirportsValid(flightDB.getDepartureFrom(), flight.getArrivalTo())) {
            flightDB.setArrivalTo(flight.getArrivalTo());
        }
        if (Objects.nonNull(flight.getDepartureFrom()) && checkIfAirportsValid(flightDB.getArrivalTo(), flight.getDepartureFrom())) {
            flightDB.setDepartureFrom(flight.getDepartureFrom());
        }
        if (Objects.nonNull(flight.getDepartureDate()) && checkIfDatesValid(flight.getDepartureDate(), flightDB.getReturnDate())) {
            flightDB.setDepartureDate(flight.getDepartureDate());
        }
        if (Objects.nonNull(flight.getReturnDate()) && checkIfDatesValid(flightDB.getDepartureDate(), flight.getReturnDate())) {
            flightDB.setReturnDate(flight.getReturnDate());
        }
        if (Objects.nonNull(flight.getFlightPrice())) {
            flightDB.setFlightPrice(flight.getFlightPrice());
        }
        return flightRepository.save(flightDB);
    }

    //DELETE
    @Override
    public void deleteFlightById(Long flightId){
        if(flightRepository.findById(flightId).isPresent()){
            flightRepository.deleteById(flightId);
        }
        else{
            throw new IllegalArgumentException(String.format("Flight with ID# %o cannot be deleted as it doesn't exist", flightId));
        }
    }

    //HELPERS
    public boolean checkIfAirportsValid(Airport departureAirport, Airport arrivalAirport){
        if(!departureAirport.getLocation().equals(arrivalAirport.getLocation())){
            return true;
        }
        else{
            throw new IllegalArgumentException("Flight departure airport cannot be the same as flight arrival airport");
        }
    }

    public boolean checkIfDatesValid(String beforeDate, String afterDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(Objects.nonNull(afterDate) ){
            try{
                if(dateFormat.parse(beforeDate).before(dateFormat.parse(afterDate))){
                    return true;
                }
                else{
                    throw new IllegalArgumentException("Flight departure date cannot be after flight return date");
                }
            } catch(ParseException e){
                throw new IllegalArgumentException("Flight departure/return date format must be of type yyyy-MM-dd HH:mm:ss");
            }
        }
        return true;
    }

    public void create1WFlights(Flight flight, Airport arrivalAirport, Airport departureAirport){
        flightRepository.save(create1WDepartureFlight(flight, arrivalAirport, departureAirport));
        flightRepository.save(create1WReturnFlight(flight, arrivalAirport, departureAirport));
    }

    public Flight create1WDepartureFlight(Flight flight, Airport arrivalAirport, Airport departureAirport){
        Flight departureFlight = new Flight();
        departureFlight.setDepartureDate(flight.getDepartureDate());
        departureFlight.setDepartureFrom(departureAirport);
        departureFlight.setArrivalTo(arrivalAirport);
        departureFlight.setFlightPrice(flight.getFlightPrice()/2); //It'd make sense from a simplistic POV for the 1-way version of a two-way ticket to have half the price
        return departureFlight;
    }

    public Flight create1WReturnFlight(Flight flight, Airport arrivalAirport, Airport departureAirport){
        Flight returnFlight = new Flight();
        returnFlight.setDepartureDate(flight.getReturnDate());
        returnFlight.setDepartureFrom(arrivalAirport);
        returnFlight.setArrivalTo(departureAirport);
        returnFlight.setFlightPrice(flight.getFlightPrice()/2);  //It'd make sense from a simplistic POV for the 1-way version of a two-way ticket to have half the price
        return returnFlight;
    }

}