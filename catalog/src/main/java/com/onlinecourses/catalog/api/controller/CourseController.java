package com.onlinecourses.catalog.api.controller;

import com.onlinecourses.catalog.api.request.CreateCourseRequest;
import com.onlinecourses.shared.api.response.ApiResponse;
import com.onlinecourses.catalog.application.command.CreateCourseCommand;
import com.onlinecourses.catalog.application.response.CourseResponse;
import com.onlinecourses.catalog.application.usecase.CreateCourseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;

    public CourseController(CreateCourseUseCase createCourseUseCase) {
        this.createCourseUseCase = createCourseUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@RequestBody CreateCourseRequest request) {
        CreateCourseCommand createCourseCommand = new CreateCourseCommand(
                request.title(),
                request.description(),
                request.price()
        );

        CourseResponse response = createCourseUseCase.execute(createCourseCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Curso creado exitosamente",
                response
        ));

    }


}
