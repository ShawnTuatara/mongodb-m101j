package course;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import course.model.LoginForm;
import course.model.SignupForm;
import course.model.User;

@Controller
public class BlogController {
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

	@InitBinder
	public void stringTrimmerBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Autowired
	private UserManager userManager;

	@Autowired
	private SessionManager sessionManager;

	@RequestMapping
	public String blog(@CookieValue(value = "session", required = false) String sessionId, Model model) {
		if (sessionId != null) {
			String username = sessionManager.findUserNameBySessionId(sessionId);
			model.addAttribute("username", username);
		}

		// this is where we would normally load up the blog data
		// but this week, we just display a placeholder.
		model.addAttribute("blogEntries", new HashMap<String, String>());

		return "blog";
	}

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

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String viewSignup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Valid SignupForm signupForm, BindingResult bindingResult, HttpServletResponse response, Model model) {
		if (bindingResult.hasErrors()) {
			return "signup";
		}

		String username = signupForm.getUsername();
		log.debug("Signup: Creating user {}", username);

		try {
			User newUser = userManager.addUser(username, signupForm.getPassword(), signupForm.getEmail());

			startSession(newUser, response);
			return "redirect:/welcome";
		} catch (UserExistsException e) {
			bindingResult.addError(new FieldError("signupForm", "username", "Username already in use, Please choose another"));
			return "signup";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(LoginForm loginForm) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postLogin(LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
		log.info("Login: User submitted: {}", loginForm.getUsername());

		User user = userManager.validateLogin(loginForm.getUsername(), loginForm.getPassword());

		if (user == null) {
			bindingResult.addError(new ObjectError("loginForm", "Invalid login. Please try again."));
			return "login";
		}

		startSession(user, response);
		return "redirect:/welcome";
	}

	@RequestMapping("/logout")
	public String logout(@CookieValue(value = "session", required = false) Cookie sessionCookie, HttpServletResponse response) {
		if (sessionCookie == null) {
			return "redirect:/login";
		}

		sessionManager.endSession(sessionCookie.getValue());
		sessionCookie.setMaxAge(0);
		response.addCookie(sessionCookie);

		return "redirect:/login";
	}

	private void startSession(User user, HttpServletResponse response) {
		String sessionId = sessionManager.startSession(user.getUsername());
		log.debug("Session ID for {} is {}", user.getUsername(), sessionId);

		response.addCookie(new Cookie("session", sessionId));
	}
}
