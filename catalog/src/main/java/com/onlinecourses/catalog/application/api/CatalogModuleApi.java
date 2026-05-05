package com.onlinecourses.catalog.application.api;

import java.util.UUID;

public interface CatalogModuleApi {
    boolean existsActiveCourse(UUID courseId);
}
