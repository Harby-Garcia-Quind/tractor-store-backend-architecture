package com.onlinecourses.enrollment.application.usecase;

import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.application.response.EnrollmentResponse;
import com.onlinecourses.enrollment.domain.exception.EnrollmentNotFoundException;
import com.onlinecourses.enrollment.domain.exception.InvalidEnrollmentException;
import com.onlinecourses.enrollment.domain.model.Enrollment;
import com.onlinecourses.enrollment.domain.model.enums.EnrollmentStatus;

import java.util.UUID;

public class UpdateEnrollmentToCancelledUseCase {
    private final EnrollmentRepository enrollmentRepository;

    public UpdateEnrollmentToCancelledUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public EnrollmentResponse execute(UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() -> new InvalidEnrollmentException("No existe el enrolamiento que buscas"
                ));
        enrollment.changeStatusCancelled();

        enrollmentRepository.save(enrollment);
        return EnrollmentResponse.fromDomain(enrollment);

    }
}
