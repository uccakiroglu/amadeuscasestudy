package net.cagatay.cakiroglu.amadeus_case_study.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Flight;

@Service
public class MockApiService {

    public List<Flight> getFromMockApi() {
        List<Flight> flights = new ArrayList<>();

        try {
            // Simulate API call and parse data from a local JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("src\\main\\resources\\flights.json");
            Map<String, Flight> flightMap = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Flight>>(){});
            flights.addAll(flightMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flights;
    }
    
}

class FlightDataWrapper {
    private Map<String, Flight> flights;
    
    public Map<String, Flight> getFlights() {
        return flights;
    }
}