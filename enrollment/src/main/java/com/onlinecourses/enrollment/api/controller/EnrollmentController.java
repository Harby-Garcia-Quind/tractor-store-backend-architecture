package com.onlinecourses.enrollment.api.controller;

import com.onlinecourses.enrollment.api.request.CreateEnrollmentRequest;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.application.usecase.GetActiveEnrollmentsByUserUseCase;
import com.onlinecourses.enrollment.application.usecase.UpdateEnrollmentToCancelledUseCase;
import com.onlinecourses.shared.api.response.ApiResponse;
import com.onlinecourses.enrollment.application.command.CreateEnrollmentCommand;
import com.onlinecourses.enrollment.application.response.EnrollmentResponse;
import com.onlinecourses.enrollment.application.usecase.CreateEnrollmentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final CreateEnrollmentUseCase createEnrollmentUseCase;
    private final GetActiveEnrollmentsByUserUseCase getActiveEnrollmentsByUserUseCase;
    private final UpdateEnrollmentToCancelledUseCase updateEnrollmentToCancelledUseCase;


    public EnrollmentController(CreateEnrollmentUseCase createEnrollmentUseCase, GetActiveEnrollmentsByUserUseCase getActiveEnrollmentsByUserUseCase, UpdateEnrollmentToCancelledUseCase updateEnrollmentToCancelledUseCase) {
        this.createEnrollmentUseCase = createEnrollmentUseCase;
        this.getActiveEnrollmentsByUserUseCase = getActiveEnrollmentsByUserUseCase;
        this.updateEnrollmentToCancelledUseCase = updateEnrollmentToCancelledUseCase;
    }

    @GetMapping("/users/{userId}/active")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getActiveEnrollmentByUser(
            @PathVariable("userId") UUID userId
    ) {
        List<EnrollmentResponse> enrollments = getActiveEnrollmentsByUserUseCase.execute(userId);
        int size = enrollments.size();
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Se encontró " + size + " inscripción activa para el usuario " + userId,
                enrollments
        ));
    }

    @PostMapping("/{enrollmentId}/cancel")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> cancelEnrollment(
            @PathVariable("enrollmentId") UUID enrollmentId
    ) {
        EnrollmentResponse response = updateEnrollmentToCancelledUseCase.execute(enrollmentId);
        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Se ha cambiado el estado con exito",
                response
        ));
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
