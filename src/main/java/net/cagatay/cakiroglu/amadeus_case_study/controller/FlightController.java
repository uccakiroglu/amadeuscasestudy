package net.cagatay.cakiroglu.amadeus_case_study.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Flight;
import net.cagatay.cakiroglu.amadeus_case_study.service.FlightService;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/flights")
    public ResponseEntity<String> saveFlight(@RequestBody Flight flight) {
        try {
            flightService.saveFlight(flight);
            return ResponseEntity.status(HttpStatus.CREATED).body("Flight created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating flight \n" + e.getMessage());
        }
    }

    //Search by airport ID
    @GetMapping("/flights/{id}")
    public ResponseEntity<?> searchFlight(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(flightService.fetchFlight(id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while retrieving flight \n%s", e.getMessage()));
        }
    }

    //Search by project's required queries
    @GetMapping("/flights")
    public ResponseEntity<?> searchFlights(@RequestParam("departure_from") Airport departureFrom, @RequestParam("arrival_at") Airport arrivalAt, @RequestParam("departure_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String departureDate, @RequestParam("return_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<String> returnDate){
         try{
            return ResponseEntity.ok(flightService.fetchFlights(departureFrom, arrivalAt, departureDate, returnDate));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while retrieving flight \n%s", e.getMessage()));
        }
    }

    @PutMapping(value="flights/{id}")
    public ResponseEntity<String> updateFlight(@RequestBody Flight flight, @PathVariable("id") Long id){
        try{
            flightService.updateFlight(flight, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("Flight with ID# %o updated successfully", id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while updating flight with ID# %o\n%s", id, e.getMessage()));
        }
    }

    @DeleteMapping("/flights/{id}")
    public ResponseEntity<String> deleteAirportById(@PathVariable("id") Long id){
        try{
            flightService.deleteFlightById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("Flight with ID# %o deleted successfully", id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while deleting flight \n%s", e.getMessage()));
        }
    }
}