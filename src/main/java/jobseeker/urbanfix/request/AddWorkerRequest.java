package jobseeker.urbanfix.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jobseeker.urbanfix.model.Category;
import lombok.Data;

@Data
public class AddWorkerRequest {
    private Long id;
    private  String name;
    private Long wages;
    private Long contact;
    private Category category;
}
