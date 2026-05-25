package com.onlinecourses.enrollment.application.usecase;

import com.onlinecourses.catalog.application.api.CatalogModuleApi;
import com.onlinecourses.enrollment.application.command.CreateEnrollmentCommand;
//import com.onlinecourses.enrollment.application.port.EnrollmentPublisher;
import com.onlinecourses.enrollment.application.command.CreateEnrollmentKafkaEvent;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.application.response.EnrollmentResponse;
import com.onlinecourses.enrollment.domain.exception.CourseNotAvailableForEnrollmentException;
import com.onlinecourses.enrollment.domain.exception.EnrollmentAlreadyExistsException;
import com.onlinecourses.enrollment.domain.exception.UserNotAvailableForEnrollmentException;
import com.onlinecourses.enrollment.domain.model.Enrollment;
import com.onlinecourses.identity.application.api.IdentityModuleApi;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;


public class CreateEnrollmentUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final IdentityModuleApi identityModuleApi;
    private final CatalogModuleApi catalogModuleApi;
//    private final EnrollmentPublisher enrollmentPublisher;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CreateEnrollmentUseCase(
            EnrollmentRepository enrollmentRepository,
            IdentityModuleApi identityModuleApi,
            CatalogModuleApi catalogModuleApi,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.identityModuleApi = identityModuleApi;
        this.catalogModuleApi = catalogModuleApi;
//        this.enrollmentPublisher = enrollmentPublisher;
        this.kafkaTemplate = kafkaTemplate;
    }

    public EnrollmentResponse execute(CreateEnrollmentCommand command) {
        validate(command);

        Enrollment enrollment = Enrollment.create(
                command.userId(),
                command.courseId()
        );

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        CreateEnrollmentKafkaEvent createEnrollmentKafkaEvent = new CreateEnrollmentKafkaEvent(savedEnrollment.getId(), BigDecimal.valueOf(5000));
        kafkaTemplate.send("enrollment-created", createEnrollmentKafkaEvent);

        return EnrollmentResponse.fromDomain(savedEnrollment);

    }

    private void validate(CreateEnrollmentCommand command) {
        if (!identityModuleApi.existsActiveUser(command.userId())) {
            throw new UserNotAvailableForEnrollmentException(command.userId());
        }

        if (!catalogModuleApi.existsActiveCourse(command.courseId())) {
            throw new CourseNotAvailableForEnrollmentException(command.courseId());
        }

        if (enrollmentRepository.existsByUserIdAndCourseId(command.userId(), command.courseId())) {
            throw new EnrollmentAlreadyExistsException(command.userId(), command.courseId());
        }
    }

}
