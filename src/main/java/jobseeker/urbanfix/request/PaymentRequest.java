package jobseeker.urbanfix.request;

import lombok.Data;

@Data
public class PaymentRequest   {
    private Long bookingId;
    private String transactionId;
    private String eventStatus;
}
