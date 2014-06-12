package course;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import course.dao.MongoUserRepository;
import course.model.User;

@Service
public class UserManager {
	private static final Logger log = LoggerFactory.getLogger(UserManager.class);

	private Random random = new SecureRandom();

	private Base64 base64 = new Base64();

	@Autowired
	private MongoUserRepository userRepository;

	public User addUser(String username, String password, String email) throws UserExistsException {
		String passwordHash = makePasswordHash(password, Integer.toString(random.nextInt()));

		User user = new User();

		user.setUsername(username);
		user.setPassword(passwordHash);
		if (!StringUtils.isEmpty(email)) {
			user.setEmail(email);
		}

		User existingUser = userRepository.findOne(username);
		if (existingUser != null) {
			throw new UserExistsException("Unable to add user. Existing user with username of " + username);
		}

		return userRepository.save(user);
	}

	public User validateLogin(String username, String password) {
		User user = userRepository.findOne(username);

		if (user == null) {
			log.warn("User not in database");
			return null;
		}

		String hashedAndSalted = user.getPassword();

		String salt = hashedAndSalted.split(",")[1];

		if (!hashedAndSalted.equals(makePasswordHash(password, salt))) {
			log.warn("Submitted password is not a match");
			return null;
		}

		return user;
	}

	private String makePasswordHash(String password, String salt) {
		try {
			String saltedAndHashed = password + "," + salt;
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(saltedAndHashed.getBytes());
			byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
			return new String(base64.encode(hashedBytes)) + "," + salt;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 is not available", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
		}
	}
}
