package net.cagatay.cakiroglu.amadeus_case_study.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Data
@Table(name = "flights")
@NoArgsConstructor
@AllArgsConstructor
public class Flight{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="departure_from", referencedColumnName="location")
    private Airport departureFrom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="arrival_to", referencedColumnName="location")
    private Airport arrivalTo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_date", columnDefinition = "DATETIME")
    private String departureDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "return_date", columnDefinition = "DATETIME")
    private String returnDate;
    private Long flightPrice;
}