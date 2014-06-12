package course;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import course.dao.MongoBlogRespository;
import course.model.BlogEntry;

@Service
public class BlogManager {
	@Autowired
	private MongoBlogRespository blogRepository;

	public List<BlogEntry> getAllSortedByDateDesc(int limit) {
		Page<BlogEntry> blogPage = blogRepository.findAll(new PageRequest(0, limit, new Sort(Direction.DESC, "postDate")));

		return blogPage.getContent();
	}

	public BlogEntry findByPermalink(String permalink) {
		return blogRepository.findByPermalink(permalink);
	}

	public String postBlogEntry(BlogEntry blogEntry) {
		String permalink = blogEntry.getTitle().replaceAll("\\s", "_"); // whitespace becomes _
		permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
		blogEntry.setPermalink(permalink.toLowerCase());
		blogEntry.setPostDate(new Date());

		BlogEntry savedEntry = blogRepository.save(blogEntry);

		return savedEntry.getPermalink();
	}
}
