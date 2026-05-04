package com.onlinecourses.identity.api.controller;

import com.onlinecourses.identity.api.request.CreateUserRequest;
import com.onlinecourses.identity.api.response.ApiResponse;
import com.onlinecourses.identity.application.command.CreateUserCommand;
import com.onlinecourses.identity.application.response.UserResponse;
import com.onlinecourses.identity.application.usecase.CreateUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(
                request.fullName(),
                request.email(),
                request.role()
        );
        System.out.println(command);

        UserResponse response = createUserUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Usuario creado exitosamente",
                response
        ));
    }


}
