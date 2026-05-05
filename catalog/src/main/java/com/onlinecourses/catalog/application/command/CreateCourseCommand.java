package com.onlinecourses.catalog.application.command;

import java.math.BigDecimal;

public record CreateCourseCommand(
        String title,
        String description,
        BigDecimal price
) {
}
