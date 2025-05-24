package me.aksaim.backendapi.utils;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

public class ValidatorUtils {
	public static boolean isValidEmail(String email) {
		return email != null && !email.isBlank() && new EmailValidator().isValid(email, null);
	}
}
