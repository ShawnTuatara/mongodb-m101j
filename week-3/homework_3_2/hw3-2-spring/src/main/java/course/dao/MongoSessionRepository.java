package course.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import course.model.Session;

public interface MongoSessionRepository extends MongoRepository<Session, String> {

}
