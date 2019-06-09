package com.uniovi.entities;

public class Associations {

	public static class Comprar{
		public static void link(Offer offer, User usuario)
		{
			offer.setPurchased(true);
			offer.setPurchaser(usuario);
			usuario.getOffersPurchased().add(offer);
		}
		
		public static void unlink(Offer offer, User usuario)
		{
			usuario.getOffersPurchased().remove(offer);
			offer.setPurchased(false);
			offer.setPurchaser(null);
		}
	}
	
	public static class Crear{
		public static void link(Offer offer, User usuario)
		{
			offer.setUser(usuario);
			usuario.getOffers().add(offer);
		}
		
		public static void unlink(Offer offer, User usuario)
		{
			usuario.getOffers().remove(offer);
			offer.setUser(null);
		}
	}
	
}
