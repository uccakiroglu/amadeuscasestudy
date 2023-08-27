package net.cagatay.cakiroglu.amadeus_case_study.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;

@Service
public interface AirportService {
    // CREATE
    Airport saveAirport(Airport airport);
    
    //READ
    Airport fetchAirport(Long id);

    List<Airport> fetchAirports(String query);

    //UPDATE
    Airport updateAirport(Airport airport, Long airportId);

    //DELETE
    void deleteAirportById(Long airportId);
}