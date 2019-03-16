package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private OffersService marksService;

	@Autowired
	private RolesService rolesService;
	
	@PostConstruct
	public void init() {
		User user1 = new User("christian@email.com", "Christian", "Peláez");
		user1.setPassword("123456");
		user1.setPasswordConfirm("123456");
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("cristina@email.com", "Cristina", "Ruíz");
		user2.setPassword("123456");
		user2.setPasswordConfirm("123456");
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("gema@email.com", "Gema", "Rico");
		user3.setPassword("123456");
		user3.setPasswordConfirm("123456");
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("noe@gmail.com", "Noe", "Fernandez");
		user4.setPassword("123456");
		user4.setPasswordConfirm("123456");
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("victoria@gmail.com", "Victoria", "Salinas");
		user5.setPassword("123456");
		user5.setPasswordConfirm("123456");
		user5.setRole(rolesService.getRoles()[0]);
		User user6 = new User("admin@email.com", "Edward", "Núñez");
		 user6.setPassword("admin");
		 user6.setPasswordConfirm("admin");
		 user6.setRole(rolesService.getRoles()[1]);
		Set user1Marks = new HashSet<Offer>() {
			{
				Offer oferta=new Offer("A1","Oferta A1", 10.0, user1);
				oferta.setPurchased(true);
				add(oferta);
				add(new Offer("A2","Oferta A2", 9.0, user1));
				add(new Offer("A3","Oferta A3", 7.0, user1));
				add(new Offer("A4","Oferta A4", 6.5, user1));
			}
		};
		user1.setOffers(user1Marks);
		Set user2Marks = new HashSet<Offer>() {
			{
				add(new Offer("B1","Oferta B1", 5.0, user2));
				add(new Offer("B2","Oferta B2", 4.3, user2));
				add(new Offer("B3","Oferta B3", 8.0, user2));
				add(new Offer("B4","Oferta B4", 3.5, user2));
			}
		};
		user2.setOffers(user2Marks);
		Set user3Marks = new HashSet<Offer>() {
			{
				;
				add(new Offer("C1","Oferta C1", 5.5, user3));
				add(new Offer("C2","Oferta C2", 6.6, user3));
				add(new Offer("C3","Oferta C3", 7.0, user3));
			}
		};
		user3.setOffers(user3Marks);
		Set user4Marks = new HashSet<Offer>() {
			{
				add(new Offer("D1","Oferta D1", 10.0, user4));
				add(new Offer("D2","Oferta D2", 8.0, user4));
				add(new Offer("D3","Oferta D3", 9.0, user4));
			}
		};
		user4.setOffers(user4Marks);
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
	}
}