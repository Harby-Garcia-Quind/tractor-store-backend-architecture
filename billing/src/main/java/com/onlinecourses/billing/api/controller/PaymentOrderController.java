package com.onlinecourses.billing.api.controller;

import com.onlinecourses.billing.api.request.CreatePaymentOrderRequest;
import com.onlinecourses.billing.api.response.ApiResponse;
import com.onlinecourses.billing.application.command.CreatePaymentOrderCommand;
import com.onlinecourses.billing.application.response.PaymentOrderResponse;
import com.onlinecourses.billing.application.usecase.CreatePaymentOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-orders")
public class PaymentOrderController {
    private final CreatePaymentOrderUseCase createPaymentOrderUseCase;

    public PaymentOrderController(CreatePaymentOrderUseCase createPaymentOrderUseCase) {
        this.createPaymentOrderUseCase = createPaymentOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<PaymentOrderResponse>> createPaymentOrder(@RequestBody CreatePaymentOrderRequest request) {

        System.out.println("Request enrollmentId: " + request.enrollmentId());
        System.out.println("Request amount: " + request.amount());

        CreatePaymentOrderCommand command = new CreatePaymentOrderCommand(
                request.enrollmentId(),
                request.amount()
        );

        PaymentOrderResponse response = createPaymentOrderUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                HttpStatus.CREATED.value(),
                "La orden ha sido creada exitosamente",
                response
        ));
    }

}
