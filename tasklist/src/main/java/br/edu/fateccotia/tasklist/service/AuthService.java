package br.edu.fateccotia.tasklist.service;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fateccotia.tasklist.model.User;

@Service
public class AuthService {
	@Autowired
	private UserService userService;

	public String authenticate(String email, String pwd) {
		Optional<User> user = userService.findByEmail(email);
		if (user.isPresent() && user.get().getPassword().equals(pwd)) {
			String token = String.format("%s|%s|%d|%d"
					, user.get().getEmail()
					, user.get().getNickname()
					, user.get().getId()
					, Instant.now().toEpochMilli());
			token = Base64.getEncoder().encodeToString(token.getBytes());
			return String.format("{\"token\":\"%s\"}", token);
		}
		return null;
	}

	public boolean validate(String token) {
		byte[] decode = Base64.getDecoder().decode(token);
		String auth = new String(decode);
		String[] split = auth.split("\\|");
		Instant instant = Instant.ofEpochMilli(Long.parseLong(split[3]));
		return instant.isAfter(Instant.now().minusSeconds(20));
	}
}
