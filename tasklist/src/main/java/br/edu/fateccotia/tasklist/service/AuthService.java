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

	/**
	 * Autentica o usuário utilizando usuário e senha e retorna um token válido por 60s
	 * @param email email do usuário
	 * @param pwd senha do usuário
	 * @return token válido por 60s
	 */
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
		String[] split = toUserArray(token);
		Instant instant = Instant.ofEpochMilli(Long.parseLong(split[3]));
		return instant.isAfter(Instant.now().minusSeconds(30));
	}

	public User toUser(String token) {
		String[] split = toUserArray(token);
		return new User(Integer.valueOf(split[2])
				,split[0]
				,null
				,split[1]);
	}
	
	private String[] toUserArray(String token) {
		byte[] decode = Base64.getDecoder().decode(token);
		String auth = new String(decode);
		String[] split = auth.split("\\|");
		return split;
	}
}
