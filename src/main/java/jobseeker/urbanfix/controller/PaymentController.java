package jobseeker.urbanfix.controller;

import jobseeker.urbanfix.request.PaymentRequest;
import jobseeker.urbanfix.response.ApiResponse;
import jobseeker.urbanfix.service.payment.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/payments")
public class PaymentController {
    private final IPaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<ApiResponse> handlePayment(@RequestBody PaymentRequest payload) {
        paymentService.processPayment(payload);

        return ResponseEntity.ok(new ApiResponse("Payment processed successfully", null));
    }
}
