package course.dao;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;

@Configuration
public class MongoConfiguration {
    @Bean
    public MongoTemplate mongoTemplate(Mongo mongo, MongoProperties mongoProperties) {
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, mongoProperties.getDatabase());
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.afterPropertiesSet();
        return new MongoTemplate(mongoDbFactory, converter);
    }
}
