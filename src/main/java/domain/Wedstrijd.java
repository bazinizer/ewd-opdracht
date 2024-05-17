package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import validation.OlympischNummerValidation;
import validation.WedstrijdDatum;

@Entity
@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wedstrijd")
public class Wedstrijd implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Sport sport;

    @ManyToOne
    private Stadium stadium;

    @ElementCollection
    private Set<String> disciplines;


    @WedstrijdDatum
    private LocalDateTime datumTijd;

    @Min(0) @Max(50)
    private int vrijePlaatsen;

    @DecimalMin("0.01") @DecimalMax("149.99")
    private double prijsPerTicket;

    @OlympischNummerValidation
    @Column(unique = true)
    private int olympicNumber1; 

    private int olympicNumber2; 



    public Wedstrijd(Sport sport, Stadium stadium, LocalDateTime datumTijd, int vrijePlaatsen, double prijsPerTicket, Set<String> disciplines, int nummer1, int nummer2) {
        this.sport = sport;
        this.stadium = stadium;
        this.datumTijd = datumTijd;
        this.vrijePlaatsen = vrijePlaatsen;
        this.prijsPerTicket = prijsPerTicket;
        this.disciplines = disciplines;
        this.olympicNumber1 = nummer1;
        this.olympicNumber2 = nummer2;
    }
    public Wedstrijd(){
    	
    }
}