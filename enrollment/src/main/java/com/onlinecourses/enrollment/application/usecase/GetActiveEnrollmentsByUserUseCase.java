package com.onlinecourses.enrollment.application.usecase;

import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.application.response.EnrollmentResponse;
import com.onlinecourses.enrollment.domain.model.Enrollment;
import com.onlinecourses.enrollment.domain.model.enums.EnrollmentStatus;

import java.util.List;
import java.util.UUID;

public class GetActiveEnrollmentsByUserUseCase {
    private final EnrollmentRepository enrollmentRepository;

    public GetActiveEnrollmentsByUserUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<EnrollmentResponse> execute (UUID userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserIdAndStatus(userId, EnrollmentStatus.ACTIVE);
        return enrollments
                .stream()
                .map(EnrollmentResponse::fromDomain)
                .toList();
    }
}
