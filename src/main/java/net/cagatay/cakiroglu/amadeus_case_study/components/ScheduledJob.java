package net.cagatay.cakiroglu.amadeus_case_study.components;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.cagatay.cakiroglu.amadeus_case_study.entities.Flight;
import net.cagatay.cakiroglu.amadeus_case_study.repositories.AirportRepository;
import net.cagatay.cakiroglu.amadeus_case_study.service.AirportService;
import net.cagatay.cakiroglu.amadeus_case_study.service.FlightService;
import net.cagatay.cakiroglu.amadeus_case_study.service.MockApiService;

@Component
public class ScheduledJob {

    private final MockApiService mockApiService;
    private final AirportService airportService;
    private final AirportRepository airportRepository;
    private final FlightService flightService;

    @Autowired
    public ScheduledJob(MockApiService mockApiService, AirportService airportService, AirportRepository airportRepository, FlightService flightService) {
        this.mockApiService = mockApiService;
        this.airportService = airportService;
        this.airportRepository = airportRepository;
        this.flightService = flightService;
    }

    @Scheduled(initialDelay = 1000, fixedDelay=Long.MAX_VALUE) //(cron = "0 0 0 * * *") // Run daily at midnight
    public void createFlightData() {
        List<Flight> mockFlights = mockApiService.getFromMockApi();
        for (Flight flight : mockFlights) {
            if (Objects.isNull(airportRepository.findByLocation(flight.getDepartureFrom().getLocation()))){
                airportService.saveAirport(flight.getDepartureFrom());
            }
            if (Objects.isNull(airportRepository.findByLocation(flight.getArrivalTo().getLocation()))){
                airportService.saveAirport(flight.getArrivalTo());
            }
            flightService.saveFlight(flight);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void retrieveFlightData() {
        List<Flight> mockFlights = mockApiService.getFromMockApi();
        for (Flight flight : mockFlights) {
            if (Objects.isNull(airportRepository.findByLocation(flight.getDepartureFrom().getLocation()))){
                airportService.saveAirport(flight.getDepartureFrom());
            }
            if (Objects.isNull(airportRepository.findByLocation(flight.getArrivalTo().getLocation()))){
                airportService.saveAirport(flight.getArrivalTo());
            }
            flightService.saveFlight(flight);
        }
    }
}
