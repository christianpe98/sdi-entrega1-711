package com.uniovi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;

public interface OffersRepository extends CrudRepository<Offer,Long>{

//	@Modifying
//	@Transactional
//	@Query("UPDATE Mark SET resend = ?1 WHERE id = ?2")
//	void updateResend(Boolean resend, Long id);
//	
	@Query("SELECT r FROM Offer r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.title) LIKE LOWER(?1))")
	Page<Offer> searchByDescriptionAndTitle(Pageable pageable, String seachtext);
//			
//	@Query("SELECT r FROM Mark r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.user.name) LIKE LOWER(?1)) AND r.user = ?2 ")
//	Page<Bid> searchByDescriptionNameAndUser(Pageable pageable, String seachtext, User user);
			@Query("SELECT r FROM Offer r WHERE r.user = ?1 ORDER BY r.id ASC ")
			List<Offer> findAllByUser(User user);
			
			Page<Offer> findAll(Pageable pageable);
	
}
