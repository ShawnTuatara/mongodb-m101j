package course;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.util.Base64Codec;

import course.dao.MongoUserRepository;
import course.model.User;

@Service
public class UserManager {
    private Random random = new SecureRandom();

    private Base64Codec base64Codec = new Base64Codec();

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

    private String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
            return base64Codec.encode(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 is not available", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
        }
    }
}
