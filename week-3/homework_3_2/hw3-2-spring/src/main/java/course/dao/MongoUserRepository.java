package course.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import course.model.User;

public interface MongoUserRepository extends MongoRepository<User, String> {

}
