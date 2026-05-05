package com.onlinecourses.enrollment.application.command;

import java.util.UUID;

public record CreateEnrollmentCommand(
        UUID userId,
        UUID courseId
) {
}
