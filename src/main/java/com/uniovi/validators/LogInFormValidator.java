package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class LogInFormValidator  implements Validator {

	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Error.empty");
		if(usersService.getUserByEmail(user.getEmail())==null)
		{
			errors.rejectValue("username", "Error.login.email.not_exist");
		}else if(!user.getPassword().equals(usersService.getUserByEmail(user.getEmail()).getPassword()))
		{
			errors.rejectValue("password", "Error.login.password.error");
		}
		
	}

}
