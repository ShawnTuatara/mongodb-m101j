package course;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.util.Base64Codec;

import course.dao.MongoSessionRepository;
import course.model.Session;

@Service
public class SessionManager {
    private Base64Codec base64Codec = new Base64Codec();

    @Autowired
    private MongoSessionRepository sessionRepository;

    public String startSession(String username) {
        // get 32 byte random number. that's a lot of bits.
        SecureRandom generator = new SecureRandom();
        byte randomBytes[] = new byte[32];
        generator.nextBytes(randomBytes);

        String sessionId = base64Codec.encode(randomBytes);

        Session session = new Session(sessionId, username);
        sessionRepository.save(session);

        return session.getSessionId();
    }
}
