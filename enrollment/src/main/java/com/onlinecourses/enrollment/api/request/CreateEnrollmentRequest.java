package com.onlinecourses.enrollment.api.request;

import java.util.UUID;

public record CreateEnrollmentRequest(
        UUID userId,
        UUID courseId
) {
}
