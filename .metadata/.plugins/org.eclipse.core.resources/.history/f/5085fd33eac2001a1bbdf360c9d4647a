package org.sid.cine;
import org.sid.cine.entities.Film;
import org.sid.cine.entities.Salle;
import org.sid.cine.entities.Ticket;
import org.sid.cine.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;


@SpringBootApplication
public class CineApplication implements CommandLineRunner{
 @Autowired
	private ICinemaInitService cinemaInitService;
 @Autowired
 private RepositoryRestConfiguration restConfiguration;
 
	public static void main(String[] args) {
		SpringApplication.run(CineApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class,Salle.class,Ticket.class);
		cinemaInitService.initVilles();
		cinemaInitService.initCinemas();
		cinemaInitService.initSalles();
		cinemaInitService.initPlaces();
		cinemaInitService.initSeance();
		cinemaInitService.initCategories();
		cinemaInitService.initFilm();
		cinemaInitService.initProjections();
		cinemaInitService.initTickets();
		
		
		
	}

}
