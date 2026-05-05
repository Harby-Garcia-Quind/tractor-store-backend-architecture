package com.onlinecourses.catalog.application.usecase;

import com.onlinecourses.catalog.application.command.CreateCourseCommand;
import com.onlinecourses.catalog.application.port.CourseRepository;
import com.onlinecourses.catalog.application.response.CourseResponse;
import com.onlinecourses.catalog.domain.exception.CourseAlreadyExistsException;
import com.onlinecourses.catalog.domain.model.Course;

public class CreateCourseUseCase {

    private final CourseRepository courseRespository;

    public CreateCourseUseCase(CourseRepository courseRespository) {
        this.courseRespository = courseRespository;
    }

    public CourseResponse execute(CreateCourseCommand command) {
        if (courseRespository.existsByTitle(command.title())) {
            throw new CourseAlreadyExistsException(command.title());
        }

        Course course = Course.create(
                command.title(),
                command.description(),
                command.price()
        );

        Course savedCourse = courseRespository.save(course);
        return CourseResponse.fromDomain(savedCourse);
    }

}
