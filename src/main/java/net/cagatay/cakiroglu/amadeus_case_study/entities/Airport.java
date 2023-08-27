package net.cagatay.cakiroglu.amadeus_case_study.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "airports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;
    //We need to make sure that there aren't multiple airports of the same city in the database. Even though in the real world, cities might have multiple airports, there are not any other identifiers
    //of airports other than their cities for this case, so we need to simplify our approach. 
    @Column(unique=true)
    private String location;
}