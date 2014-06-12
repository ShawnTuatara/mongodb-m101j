package course.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import course.model.BlogEntry;

public interface MongoBlogRespository extends MongoRepository<BlogEntry, String> {

	BlogEntry findByPermalink(String permalink);
}
