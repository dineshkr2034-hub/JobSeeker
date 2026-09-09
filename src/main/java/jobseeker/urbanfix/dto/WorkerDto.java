package jobseeker.urbanfix.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jobseeker.urbanfix.model.Category;
import lombok.Data;

@Data
public class WorkerDto {
    private Long id;
    private  String name;
    private Long wages;
    private Category category;
}
