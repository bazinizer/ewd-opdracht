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
@NoArgsConstructor
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wedstrijd_id")
    private Wedstrijd wedstrijd;

    @Column(nullable = false)
    private int aantal;

    @Column(nullable = false)
    private double totalePrijs;

    @Column(nullable = false)
    private LocalDateTime aankoopTijd = LocalDateTime.now();

//    public Ticket() {}

    public Ticket(Wedstrijd wedstrijd, MyUser user, int aantal, double totalePrijs) {
        this.wedstrijd = wedstrijd;
        this.user = user;
        this.aantal = aantal;
        this.totalePrijs = totalePrijs;
        this.aankoopTijd = LocalDateTime.now();
    }
}
