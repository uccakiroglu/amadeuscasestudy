package net.cagatay.cakiroglu.amadeus_case_study.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.cagatay.cakiroglu.amadeus_case_study.entities.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>{

    // When we need to search for airports that match or are similar to the given query.
    @Query("SELECT a FROM Airport a WHERE " +
            "a.location LIKE CONCAT('%', :query, '%')")
    List<Airport> searchAirports(String query);

    // When we need to find the airports by their given location.
    Airport findByLocation(String location);

}
