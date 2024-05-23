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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import validation.DateTimeValidation;
import validation.OlympicNumber1Validation;
import validation.OlympicNumber2Validation;

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

    @NotNull(message = "{datum.required}")
    @DateTimeValidation
    @Column(name = "datum_tijd")
    private LocalDateTime datumTijd;

    @DecimalMin(value = "0.01", message = "{prijsPerTicket.range.invalid}")
    @DecimalMax(value = "149.99", message = "{prijsPerTicket.range.invalid}")
    private double prijsPerTicket;

    @Min(value = 1, message = "{vrijePlaatsen.range.invalid}")
    @Max(value = 50, message = "{vrijePlaatsen.range.invalid}")
    private int vrijePlaatsen;

    @Column(unique = true)
    @OlympicNumber1Validation
    private int olympicNumber1;

    @OlympicNumber2Validation
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

    public Wedstrijd() {
    }

    @Override
    public String toString() {
        return "Wedstrijd{" +
                "id=" + id +
                ", sport=" + (sport != null ? sport.getNaam() : "null") +
                ", stadium=" + (stadium != null ? stadium.getNaam() : "null") +
                ", disciplines=" + disciplines +
                ", datumTijd=" + datumTijd +
                ", prijsPerTicket=" + prijsPerTicket +
                ", vrijePlaatsen=" + vrijePlaatsen +
                ", olympicNumber1=" + olympicNumber1 +
                ", olympicNumber2=" + olympicNumber2 +
                '}';
    }
}
