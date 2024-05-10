package domain;



import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ticket")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MyUser user;

    @ManyToOne
    private Wedstrijd wedstrijd;

    private int aantal;
    private double totalePrijs;
    private LocalDateTime aankoopTijd;

//    public Ticket() {}

    public Ticket(Wedstrijd wedstrijd, MyUser user, int aantal, double totalePrijs) {
        this.wedstrijd = wedstrijd;
        this.user = user;
        this.aantal = aantal;
        this.totalePrijs = totalePrijs;
        this.aankoopTijd = LocalDateTime.now();
    }
}
