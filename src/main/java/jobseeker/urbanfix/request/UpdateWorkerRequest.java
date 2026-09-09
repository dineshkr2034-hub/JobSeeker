package jobseeker.urbanfix.request;

import jobseeker.urbanfix.model.Category;
import lombok.Data;

@Data
public class UpdateWorkerRequest {
    private Long id;
    private  String name;
    private Long wages;
    private Long contact;
    private Category category;
}
