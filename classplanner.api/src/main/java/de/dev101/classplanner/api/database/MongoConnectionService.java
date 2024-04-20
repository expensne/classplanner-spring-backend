package de.dev101.classplanner.api.database;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoConnectionService {

    private final MongoTemplate mongoTemplate;

    public MongoConnectionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public String checkConnection() {
        try {
            mongoTemplate.getDb().runCommand(new Document("ping", 1));
            return "Connected to MongoDB!";
        } catch (Exception e) {
            return "Failed to connect to MongoDB: " + e.getMessage();
        }
    }
}
