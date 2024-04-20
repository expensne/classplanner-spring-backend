package de.dev101.classplanner.api.endpoints;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import de.dev101.classplanner.api.endpoints.exams.ExamValidator;
import de.dev101.classplanner.api.endpoints.students.StudentValidator;

@Configuration
public class ValidationConfig implements RepositoryRestConfigurer {

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        var validators = List.of(new StudentValidator(), new ExamValidator());
        validators.forEach(v -> validatingListener.addValidator("beforeCreate", v));
        validators.forEach(v -> validatingListener.addValidator("beforeSave", v));
    }
}
