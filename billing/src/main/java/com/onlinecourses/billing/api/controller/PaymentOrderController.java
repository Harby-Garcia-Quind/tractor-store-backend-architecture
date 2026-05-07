package com.onlinecourses.billing.api.controller;

import com.onlinecourses.billing.api.request.CreatePaymentOrderRequest;
import com.onlinecourses.billing.api.response.ApiResponse;
import com.onlinecourses.billing.application.command.CreatePaymentOrderCommand;
import com.onlinecourses.billing.application.response.PaymentOrderResponse;
import com.onlinecourses.billing.application.usecase.CreatePaymentOrderUseCase;
import com.onlinecourses.billing.application.usecase.PayPaymentOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment-orders")
public class PaymentOrderController {
    private final CreatePaymentOrderUseCase createPaymentOrderUseCase;
    private final PayPaymentOrderUseCase payPaymentOrderUseCase;

    public PaymentOrderController(CreatePaymentOrderUseCase createPaymentOrderUseCase, PayPaymentOrderUseCase payPaymentOrderUseCase) {
        this.createPaymentOrderUseCase = createPaymentOrderUseCase;
        this.payPaymentOrderUseCase = payPaymentOrderUseCase;
    }

    @PostMapping("/{paymentOrderId}/pay")
    public ResponseEntity<ApiResponse<PaymentOrderResponse>> payPaymentOrder(
            @PathVariable("paymentOrderId") UUID paymentOrderId
    ) {
        PaymentOrderResponse response = payPaymentOrderUseCase.execute(paymentOrderId);

        return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.ACCEPTED.value(),
                "Se ha actualizado la orden de pago con éxito" + response.status(),
                response
        ));
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
