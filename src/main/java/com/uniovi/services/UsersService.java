package com.uniovi.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		if(user.getPassword().equals(user.getPasswordConfirm()))
		{
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			usersRepository.save(user);
		}
		else {
			System.out.println("contraseñas no coinciden");
		}
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}
	
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
		}

	public void update(User user1) {
		usersRepository.save(user1);
		
	}
}
