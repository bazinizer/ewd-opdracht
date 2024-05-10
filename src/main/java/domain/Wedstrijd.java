package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDateTime datumTijd;
    private int vrijePlaatsen;
    private double prijsPerTicket;



    public Wedstrijd(Sport sport, Stadium stadium, LocalDateTime datumTijd, int vrijePlaatsen, double prijsPerTicket, Set<String> disciplines) {
        this.sport = sport;
        this.stadium = stadium;
        this.datumTijd = datumTijd;
        this.vrijePlaatsen = vrijePlaatsen;
        this.prijsPerTicket = prijsPerTicket;
        this.disciplines = disciplines;
    }
    public Wedstrijd(){
    	
    }
}