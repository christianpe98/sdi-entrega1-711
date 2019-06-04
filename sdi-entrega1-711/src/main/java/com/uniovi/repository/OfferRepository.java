package com.uniovi.repository;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

public interface OfferRepository extends CrudRepository<Offer, Long>{

}
