package net.cagatay.cakiroglu.amadeus_case_study.service;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Flight;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service; 

@Service
public interface FlightService {
    // CREATE
    Flight saveFlight(Flight flight);
    boolean checkIfAirportsValid(Airport departureAirport, Airport arrivalAirport);
    boolean checkIfDatesValid(String beforeDate, String afterDate);
    void create1WFlights(Flight flight, Airport arrivalAirport, Airport departureAirport);
    Flight create1WDepartureFlight(Flight flight, Airport arrivalAirport, Airport departureAirport);
    Flight create1WReturnFlight(Flight flight, Airport arrivalAirport, Airport departureAirport);
    
    //READ
    Flight fetchFlight(Long id);
    List<Flight> fetchFlights(Airport departureFrom, Airport arrivalTo, String departureDate, Optional<String> arrivalDate);

    // //UPDATE
    Flight updateFlight(Flight flight, Long flightId);

    //DELETE
    void deleteFlightById(Long flightId);
}
