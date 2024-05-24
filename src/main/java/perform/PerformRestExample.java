package perform;

import org.springframework.web.reactive.function.client.WebClient;

import domain.Wedstrijd;
import reactor.core.publisher.Mono;

public class PerformRestExample {
	

	
	
    private final String SERVER_URI = "http://localhost:8080/rest";
    private WebClient webClient = WebClient.create();

    public PerformRestExample() {
    	System.out.println("\n --------- AANTAL PLAATSEN VOOR WEDSTRIJD ----------");
    	System.out.println(" --------- GET BY ID 1 ----------");
    	getWedstrijdAvailability(1);
    	
    	System.out.println("\n --------- AANTAL PLAATSEN VOOR WEDSTRIJD ----------");
    	System.out.println(" --------- GET BY ID 2 ----------");
    	getWedstrijdAvailability(2);
    	
    	try {
    	System.out.println("\n --------- AANTAL PLAATSEN VOOR WEDSTRIJD ----------");
    	System.out.println(" --------- GET BY ID 50 ----------");
    	getWedstrijdAvailability(50);
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    	System.out.println("\n --------- WEDSTRIJDEN PER SPORT ----------");
    	System.out.println(" --------- SPORT 1 ----------");
    	getWedstrijdenBySport(1);
    	
    	System.out.println("\n --------- WEDSTRIJDEN PER SPORT  ----------");
    	System.out.println(" --------- SPORT 2 ----------");
    	getWedstrijdenBySport(2);
    	
    	try {
        	System.out.println("\n --------- WEDSTRIJDEN PER SPORT ----------");
        	System.out.println(" --------- SPORT 10 ----------");
        	getWedstrijdenBySport(10);
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    	
    }
   

    private void getWedstrijdAvailability(long wedstrijdId) {
        webClient.get()
            .uri(SERVER_URI + "/wedstrijdAvailability/" + wedstrijdId)
            .retrieve()
            .bodyToMono(Wedstrijd.class)
            .doOnSuccess(wedstrijd -> {
            	printWedstrijdvrijeData(wedstrijd);
            })
            .block();
    }
    private void getWedstrijdenBySport(long sportId) {
        webClient.get()
            .uri(SERVER_URI + "/sport/" + sportId)
            .retrieve()
            .bodyToFlux(Wedstrijd.class).flatMap(wedstrijd -> {
            	printWedstrijdAllData(wedstrijd);
            	return Mono.empty();
            })
        .blockLast();
            
    }
    
    
    
    private void printWedstrijdAllData(Wedstrijd wedstrijd) {
        System.out.printf("wedstrijd id=%s,vrije plaatsen=%s, datum=%s%n",
                wedstrijd.getId(),
                wedstrijd.getVrijePlaatsen(),
                wedstrijd.getDatumTijd());
    }
    private void printWedstrijdvrijeData(Wedstrijd wedstrijd) {
        System.out.printf("vrije plaatsen=%s",
                wedstrijd.getVrijePlaatsen());

    }
    

}
