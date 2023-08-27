package net.cagatay.cakiroglu.amadeus_case_study.controller;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;
import net.cagatay.cakiroglu.amadeus_case_study.service.AirportService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    // POST
    @PostMapping("/airports")
    public ResponseEntity<String> saveAirport(@Validated @RequestBody Airport airport) {
        try {
            airportService.saveAirport(airport);
            return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Airport at %s created successfully with ID# %o", airport.getLocation(), airport.getAirportId()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while creating airport\n%s", e.getMessage()));
        }
    }

    // GET
    // Get airport by ID
    @GetMapping("/airports/{id}")
    public ResponseEntity<?> searchAirport(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(airportService.fetchAirport(id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while retrieving airport \n%s", e.getMessage()));
        }
    }

    // Get airport by query
    @GetMapping("/airports")
    public ResponseEntity<?> searchAirports(@RequestParam("query") String query){
        try{
            return ResponseEntity.ok(airportService.fetchAirports(query));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while retrieving airports \n%s", e.getMessage()));
        }
    }
    
    // PUT
    @PutMapping("/airports/{id}")
    public ResponseEntity<String> updateAirport(@RequestBody Airport airport, @PathVariable("id") Long id){
        try{
            airportService.updateAirport(airport, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("Airport with ID# %o updated successfully", id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while updating airport with ID# %o\n%s", id, e.getMessage()));
        }
    }

    // DELETE
    @DeleteMapping("/airports/{id}")
    public ResponseEntity<String> deleteAirportById(@PathVariable("id") Long id){
        try{
            airportService.deleteAirportById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.format("Airport with ID# %o deleted successfully", id));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error while deleting airport \n%s", e.getMessage()));
        }
    }
}