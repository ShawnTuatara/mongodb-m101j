package course;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import course.model.SignupForm;
import course.model.User;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
public class BlogController {
    private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private SessionManager sessionManager;

    @InitBinder
    public void stringTrimmerBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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

        log.debug("Signup: Creating user {}", signupForm.getUsername());

        try {
            User newUser = userManager.addUser(signupForm.getUsername(), signupForm.getPassword(), signupForm.getEmail());

            String sessionId = sessionManager.startSession(signupForm.getUsername());
            log.debug("Session ID for {} is {}", newUser.getUsername(), sessionId);

            response.addCookie(new Cookie("session", sessionId));
            return "redirect:/welcome";
        } catch (UserExistsException e) {
            bindingResult.addError(new FieldError("signupForm", "username", "Username already in use, Please choose another"));
            return "signup";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogController.class, args);
    }
}
