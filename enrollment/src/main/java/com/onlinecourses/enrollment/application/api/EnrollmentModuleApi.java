package com.onlinecourses.enrollment.application.api;

import java.util.UUID;

public interface EnrollmentModuleApi {
    boolean existsPendingEnrollment(UUID enrollmentId);
}
