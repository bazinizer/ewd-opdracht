package domain;

import java.io.Serializable;
import java.util.Set;

import org.eclipse.persistence.internal.oxm.schema.model.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sport")
public class Sport implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naam;
    
    @ElementCollection
    private Set<String> disciplines;

    @OneToMany(mappedBy = "sport")
    private Set<Wedstrijd> wedstrijden;

    public Sport() {}

    public Sport(String naam, Set<String> disciplines ) {
        this.naam = naam;
        this.disciplines = disciplines;
    }
}


