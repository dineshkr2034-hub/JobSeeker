package jobseeker.urbanfix.service.payment;

import jobseeker.urbanfix.request.PaymentRequest;

public interface IPaymentService {
    void processPayment(PaymentRequest payload);
}
