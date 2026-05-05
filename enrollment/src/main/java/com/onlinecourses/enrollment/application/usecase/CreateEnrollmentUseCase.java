package com.onlinecourses.enrollment.application.usecase;

import com.onlinecourses.catalog.application.api.CatalogModuleApi;
import com.onlinecourses.enrollment.application.command.CreateEnrollmentCommand;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.application.response.EnrollmentResponse;
import com.onlinecourses.enrollment.domain.exception.CourseNotAvailableForEnrollmentException;
import com.onlinecourses.enrollment.domain.exception.EnrollmentAlreadyExistsException;
import com.onlinecourses.enrollment.domain.exception.UserNotAvailableForEnrollmentException;
import com.onlinecourses.enrollment.domain.model.Enrollment;
import com.onlinecourses.identity.application.api.IdentityModuleApi;


public class CreateEnrollmentUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final IdentityModuleApi identityModuleApi;
    private final CatalogModuleApi catalogModuleApi;

    public CreateEnrollmentUseCase(
            EnrollmentRepository enrollmentRepository,
            IdentityModuleApi identityModuleApi,
            CatalogModuleApi catalogModuleApi
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.identityModuleApi = identityModuleApi;
        this.catalogModuleApi = catalogModuleApi;
    }

    public EnrollmentResponse execute(CreateEnrollmentCommand command) {
        validate(command);

        Enrollment enrollment = Enrollment.create(
                command.userId(),
                command.courseId()
        );

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

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
