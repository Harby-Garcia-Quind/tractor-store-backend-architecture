package com.onlinecourses.enrollment.application.service;

import com.onlinecourses.enrollment.application.api.EnrollmentModuleApi;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.domain.model.enums.EnrollmentStatus;

import java.util.UUID;

public class EnrollmentModuleService implements EnrollmentModuleApi {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentModuleService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public boolean existsPendingEnrollment(UUID enrollmentId) {
        return enrollmentRepository.existsByIdAndStatus(
                enrollmentId,
                EnrollmentStatus.PENDING_PAYMENT
        );
    }

    @Override
    public void activateEnrollment(UUID enrollmentId) {

    }
}
