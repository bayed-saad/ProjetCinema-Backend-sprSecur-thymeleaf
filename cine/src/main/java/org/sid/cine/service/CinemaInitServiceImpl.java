package org.sid.cine.service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import org.sid.cine.dao.CategorieRepository;
import org.sid.cine.dao.CinemaRepository;
import org.sid.cine.dao.PlaceRepository;
import org.sid.cine.dao.SalleRepository;
import org.sid.cine.dao.VilleRepository;
import org.sid.cine.dao.ProjectionRepository;
import org.sid.cine.dao.SeanceRepository;
import org.sid.cine.dao.FilmRepository;
import org.sid.cine.dao.TicketRepository;
import org.sid.cine.entities.Categorie;
import org.sid.cine.entities.Cinema;
import org.sid.cine.entities.Film;
import org.sid.cine.entities.Place;
import org.sid.cine.entities.Projection;
import org.sid.cine.entities.Salle;
import org.sid.cine.entities.Seance;
import org.sid.cine.entities.Ticket;
import org.sid.cine.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{
 @Autowired 	private VilleRepository villeRepository;
 @Autowired 	private CinemaRepository cinemaRepository;
 @Autowired 	private SalleRepository salleRepository;
 @Autowired 	private PlaceRepository placeRepository;
 @Autowired 	private SeanceRepository SeanceRepository;
 @Autowired 	private FilmRepository filmRepository;
 @Autowired 	private ProjectionRepository projectionRepository;
 @Autowired 	private TicketRepository ticketRepository;
 @Autowired     private CategorieRepository categorieRepository;
	
 @Override
	public void initVilles() {
		Stream.of("Casablanca","Marrakech","Tanger","Rabat").forEach(nameVille->{
			Ville ville = new Ville(); ville.setName(nameVille);villeRepository.save(ville);
		});		
	}
	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v->{
			Stream.of("Megarama","IMAX","ABC","LYNX").forEach(nameCinema->{
				Cinema cinema = new Cinema();cinema.setName(nameCinema);
				cinema.setNombreSalles(3+(int)(Math.random()*7));
				cinema.setVille(v);cinemaRepository.save(cinema);});
		});
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema->{
		for(int i=0;i<cinema.getNombreSalles();i++) {
			Salle salle =new Salle();salle.setName("Sale"+(i+1));
			salle.setCinema(cinema);salle.setNombrePlace(15+(int)(Math.random()*20));
			salleRepository.save(salle);}					
		});		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{ 
			for(int i=0;i<salle.getNombrePlace();i++) {
				Place place =new Place();place.setNumero(i+1);
				place.setSalle(salle);placeRepository.save(place);}				
		});		
	}

	@Override
	public void initSeance() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","14:00","17:00","19:00","21:00").forEach(s->{
			Seance seance =new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));SeanceRepository.save(seance);
			} catch (ParseException e) {				
				e.printStackTrace();
			}					
		});	
		}

	@Override
	public void initCategories() {
		Stream.of("Histoire","Actions","Drama","Fiction","Thriller").forEach(cat->{
			Categorie categorie = new Categorie();categorie.setName(cat);
			categorieRepository.save(categorie);
						});		
	}

	@Override
	public void initFilm() {
		double[] durees = new double [] {1,1.5,2.5,3}; 
		List<Categorie> categories =categorieRepository.findAll();
		Stream.of("12Hommesencolere","ForrestGump","GreenBook","LaLigneVerte","LeParrain","LeSeigneurDesAnneaux").forEach(titreFilm->
		{   Film film = new Film();	film.setTitre(titreFilm);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(titreFilm.replaceAll("", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
		    filmRepository.save(film);});
		
		
	}

	@Override
	public void initProjections() {
		double[] price = new double[] {30,45,50,65,70,100};
		List<Film> films =filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int	index = new Random().nextInt(films.size());	
					Film film=films.get(index);
						SeanceRepository.findAll().forEach(seance->{
							Projection projection =new Projection();projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(price[new Random().nextInt(price.length)]);
						    projection.setSalle(salle); projection.setSeance(seance);
						    projectionRepository.save(projection);});
					});
				
			});
		});
		
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(p->{
			p.getSalle().getPlaces().forEach(place->{
				Ticket ticket =new Ticket();ticket.setPlace(place);	ticket.setPrix(p.getPrix());
				ticket.setProjection(p);ticket.setReserve(false);ticketRepository.save(ticket);});
		});		
	}
}
