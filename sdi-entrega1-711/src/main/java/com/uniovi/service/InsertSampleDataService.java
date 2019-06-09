package com.uniovi.service;

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
	private OffersService offerService;

	@Autowired
	private RolesService rolesService;
	
	public void removeAll()
	{
		usersService.removeAll();
		offerService.removeAll();
	}
	
	
	@SuppressWarnings("serial")
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
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		
		Offer CH1= new Offer("CH1", "Oferta CH1", 10.0, user1);
		CH1.setPurchased(true);
		Offer CH2=	new Offer("CH2", "Oferta CH2", 9.0, user1);
		CH2.setPurchased(true);
		Offer CH3=new Offer("CH3", "Oferta CH3", 7.0, user1);
		Offer CH4=new Offer("CH4", "Oferta CH4", 6.5, user1);
		Set<Offer> user1Marks = new HashSet<Offer>() {
			{
				add(CH1);
				add(CH2);
				add(CH3);
				add(CH4);
			}
		};
		user1.setOffers(user1Marks);
		
		Offer CR1=new Offer("CR1", "Oferta CR1", 5.0, user2);
		CR1.setPurchased(true);
		Offer CR2=new Offer("CR2", "Oferta CR2", 4.3, user2);
		CR2.setPurchased(true);
		Offer CR3=new Offer("CR3", "Oferta CR3", 8.0, user2);
		Offer CR4=new Offer("CR4", "Oferta CR4", 3.5, user2);
		Set<Offer> user2Marks = new HashSet<Offer>() {
			{
				add(CR1);
				add(CR2);
				add(CR3);
				add(CR4);
			}
		};
		
		Offer GE1=new Offer("GE1", "Oferta GE1", 5.5, user3);
		GE1.setPurchased(true);
		Offer GE2=new Offer("GE2", "Oferta GE2", 6.6, user3);
		GE2.setPurchased(true);
		Offer GE3=new Offer("GE3", "Oferta GE3", 7.0, user3);
		user2.setOffers(user2Marks);
		Set<Offer> user3Marks = new HashSet<Offer>() {
			{
				;
				add(GE1);
				add(GE2);
				add(GE3);
			}
		};
		user3.setOffers(user3Marks);
		
			
		Offer NO1=new Offer("NO1", "Oferta NO1", 10.0, user4);
		NO1.setPurchased(true);
		Offer NO2=new Offer("NO2", "Oferta NO2", 8.0, user4);
		NO2.setPurchased(true);
		Offer NO3=new Offer("NO3", "Oferta NO3", 9.0, user4);
		Set<Offer> user4Marks = new HashSet<Offer>() {
			{
				add(NO1);
				add(NO2);
				add(NO3);
			}
		};

		user4.setOffers(user4Marks);
		
		
		Offer VI1=new Offer("VI1", "Oferta VI1", 10.0, user4);
		VI1.setPurchased(true);
		Offer VI2=new Offer("VI2", "Oferta VI2", 8.0, user4);
		VI2.setPurchased(true);
		Offer VI3=new Offer("VI3", "Oferta VI3", 9.0, user4);
		Set<Offer> user5Marks = new HashSet<Offer>() {
			{
				add(VI1);
				add(VI2);
				add(VI3);
			}
		};
		
		usersService.update(user1);
		usersService.update(user3);
		usersService.update(user4);
		usersService.update(user5);
		usersService.update(user6);
		
		user5.setOffers(user5Marks);		
		
		user1.getOffersPurchased().add(CR1);
		user1.getOffersPurchased().add(CR2);
		user2.getOffersPurchased().add(GE1);
		user2.getOffersPurchased().add(GE2);
		user3.getOffersPurchased().add(NO1);
		user3.getOffersPurchased().add(NO2);
		user4.getOffersPurchased().add(VI1);
		user4.getOffersPurchased().add(VI2);
		user5.getOffersPurchased().add(CH1);
		user5.getOffersPurchased().add(CH2);
		
		
		
		
		CR1.setPurchaser(user1);
		CR2.setPurchaser(user1);
		offerService.update(CR1);
		offerService.update(CR2);
		
		GE1.setPurchaser(user2);
		GE2.setPurchaser(user2);
		offerService.update(GE1);
		offerService.update(GE2);
		
		NO1.setPurchaser(user3);
		NO2.setPurchaser(user3);		
		offerService.update(NO1);
		offerService.update(NO2);
		
		VI1.setPurchaser(user4);
		VI2.setPurchaser(user4);
		offerService.update(VI1);
		offerService.update(VI2);
		
		CH1.setPurchaser(user5);
		CH1.setPurchaser(user5);
		offerService.update(CH1);
		offerService.update(CH1);
		
		
	}
}