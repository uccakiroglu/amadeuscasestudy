package net.cagatay.cakiroglu.amadeus_case_study.service;

import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;
import net.cagatay.cakiroglu.amadeus_case_study.repositories.AirportRepository;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class AirportServiceImpl implements AirportService{
    @Autowired
    private AirportRepository airportRepository;
    
    // CREATE
    @Override
    public Airport saveAirport(Airport airport){
        try{
            return airportRepository.save(airport);
        }catch (DataIntegrityViolationException e) {
            // Handle the case where an airport with the same location exists
            throw new IllegalArgumentException(String.format("Airport at %s already exists in the database with ID# %o", airport.getLocation(), airportRepository.findByLocation(airport.getLocation()).getAirportId()));
        }
    }
    
    //READ
    //BY ID
    @Override
    public Airport fetchAirport(Long id){
        try{
            Airport airport = airportRepository.findById(id).get();
            return airport;
        }catch(Exception e){
            throw new IllegalArgumentException("No airport with the given ID found");
        }
    }

    //BY QUERY
    @Override
    public List<Airport> fetchAirports(String query){
        List<Airport> airports = airportRepository.searchAirports(query);
        if(airports.size() == 0){
            throw new IllegalArgumentException("No airports with the given query found");
        }
        return airports;
    }

    //UPDATE
    @Override
    public Airport updateAirport(Airport airport, Long airportId){
        try{
            Airport airportDB = airportRepository.findById(airportId).get();
            if (Objects.nonNull(airport.getLocation())) {
                airportDB.setLocation(airport.getLocation());
            }
            return airportRepository.save(airportDB);
        } catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException(String.format("Airport cannot be updated as the airport at %s already exists", airport.getLocation()));
        }
    }

    //DELETE
    @Override
    public void deleteAirportById(Long airportId){
        if (airportRepository.findById(airportId).isPresent()){
            airportRepository.deleteById(airportId);
        }
        else{
            throw new IllegalArgumentException(String.format("Airport with ID# %o cannot be deleted as it doesn't exist", airportId));
        }
    }
}
