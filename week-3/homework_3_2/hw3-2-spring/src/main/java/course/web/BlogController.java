package course.web;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import course.BlogManager;
import course.SessionManager;
import course.model.BlogEntry;
import course.web.model.BlogEntryForm;

@Controller
public class BlogController {
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private BlogManager blogManager;

	@RequestMapping("/")
	public String blog(@CookieValue(value = "session", required = false) String sessionId, Model model) {
		if (sessionId != null) {
			String username = sessionManager.findUserNameBySessionId(sessionId);
			model.addAttribute("username", username);
		}

		List<BlogEntry> blogEntries = blogManager.getAllSortedByDateDesc(10);
		model.addAttribute("blogEntries", blogEntries);

		return "blog";
	}

	@RequestMapping("/post/{permalink}")
	public String blogEntry(@CookieValue(value = "session", required = false) String sessionId, @PathVariable String permalink, Model model) {
		log.info("/post: get {}", permalink);

		if (sessionId != null) {
			String username = sessionManager.findUserNameBySessionId(sessionId);
			model.addAttribute("username", username);
		}

		BlogEntry blogEntry = blogManager.findByPermalink(permalink);
		if (blogEntry == null) {
			return "redirect:/post_not_found";
		}

		model.addAttribute("blogEntry", blogEntry);

		return "blog-entry";
	}

	@RequestMapping(value = { "/newpost", "/post" }, method = RequestMethod.GET)
	public String blogEntryForm(@CookieValue(value = "session", required = false) String sessionId, Model model) {
		if (sessionId == null) {
			log.info("No session found, redirecting to login");
			return "redirect:/login";
		}

		String username = sessionManager.findUserNameBySessionId(sessionId);
		if (username == null) {
			log.info("Can't identify the user with session id of {}, redirecting to login", sessionId);
			return "redirect:/login";
		}
		model.addAttribute("username", username);

		return "blog-entry-form";
	}

	@RequestMapping(value = { "/newpost", "/post" }, method = RequestMethod.POST)
	public String blogEntrySubmission(@CookieValue(value = "session", required = false) String sessionId, @Valid BlogEntryForm blogEntryForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "blog-entry-form";
		}

		if (sessionId == null) {
			log.info("No session found, redirecting to login");
			return "redirect:/login";
		}

		String username = sessionManager.findUserNameBySessionId(sessionId);
		if (username == null) {
			log.info("Can't identify the user with session id of {}, redirecting to login", sessionId);
			return "redirect:/login";
		}

		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setAuthor(username);
		blogEntry.setTitle(blogEntryForm.getSubject());
		blogEntry.setBody(blogEntryForm.getBody());
		String[] tags = StringUtils.split(blogEntryForm.getTags(), " ,");
		blogEntry.setTags(Arrays.asList(tags));

		String permalink = blogManager.postBlogEntry(blogEntry);

		return "redirect:/post/" + permalink;
	}

	@RequestMapping("/post_not_found")
	public String postNotFound() {
		return "blog-entry-not-found";
	}
}
