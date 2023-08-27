package net.cagatay.cakiroglu.amadeus_case_study.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Flight;


@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{
    List<Flight> findByDepartureFromAndArrivalToAndDepartureDateAndReturnDate(Airport departureFrom, Airport arrivalTo, String departureDate, Optional<String> returnDate);

}
