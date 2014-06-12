package course.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import course.SessionManager;

@Controller
public class WelcomeController {
	private static Logger log = LoggerFactory.getLogger(WelcomeController.class);

	@Autowired
	private SessionManager sessionManager;

	@RequestMapping("/welcome")
	public String welcome(@CookieValue(value = "session", required = false) String sessionId, Model model) {
		if (sessionId == null) {
			return "redirect:/signup";
		}

		String username = sessionManager.findUserNameBySessionId(sessionId);
		if (username == null) {
			log.info("welcome() can't identify the user with session id of {}, redirecting to signup", sessionId);
			return "redirect:/signup";
		}

		model.addAttribute("username", username);
		return "welcome";
	}
}
