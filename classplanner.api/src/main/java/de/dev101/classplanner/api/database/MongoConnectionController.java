package de.dev101.classplanner.api.database;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoConnectionController {

    private final MongoConnectionService mongoConnectionService;

    public MongoConnectionController(MongoConnectionService mongoConnectionService) {
        this.mongoConnectionService = mongoConnectionService;
    }

    @GetMapping("/check")
    public String checkMongoConnection() {
        return mongoConnectionService.checkConnection();
    }
}