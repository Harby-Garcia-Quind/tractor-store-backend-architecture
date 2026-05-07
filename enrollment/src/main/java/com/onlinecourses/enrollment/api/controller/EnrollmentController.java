package com.onlinecourses.enrollment.api.controller;

import com.onlinecourses.enrollment.api.request.CreateEnrollmentRequest;
import com.onlinecourses.enrollment.api.response.ApiResponse;
import com.onlinecourses.enrollment.application.command.CreateEnrollmentCommand;
import com.onlinecourses.enrollment.application.response.EnrollmentResponse;
import com.onlinecourses.enrollment.application.usecase.CreateEnrollmentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final CreateEnrollmentUseCase createEnrollmentUseCase;


    public EnrollmentController(CreateEnrollmentUseCase createEnrollmentUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<EnrollmentResponse>> createEnrollment(@RequestBody CreateEnrollmentRequest request) {
        CreateEnrollmentCommand command = new CreateEnrollmentCommand(request.userId(), request.courseId());

        EnrollmentResponse response = createEnrollmentUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Se ha registrado el enrolamiento del usuario: " + request.userId() +" con el curso: " + request.courseId(),
                response
        ));
    }

}
