package com.onlinecourses.catalog.api.request;

import java.math.BigDecimal;

public record CreateCourseRequest(
        String title,
        String description,
        BigDecimal price
) {
}
