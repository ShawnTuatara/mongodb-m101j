package course;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.dao.MongoSessionRepository;
import course.model.Session;

@Service
public class SessionManager {
	private Base64 base64 = new Base64();

	@Autowired
	private MongoSessionRepository sessionRepository;

	public String startSession(String username) {
		// get 32 byte random number. that's a lot of bits.
		SecureRandom generator = new SecureRandom();
		byte randomBytes[] = new byte[32];
		generator.nextBytes(randomBytes);

		String sessionId = new String(base64.encode(randomBytes));

		Session session = new Session(sessionId, username);
		sessionRepository.save(session);

		return session.getSessionId();
	}

	public String findUserNameBySessionId(String sessionId) {
		Session session = sessionRepository.findOne(sessionId);

		return session == null ? null : session.getUsername();
	}

	public void endSession(String sessionId) {
		sessionRepository.delete(sessionId);
	}
}
