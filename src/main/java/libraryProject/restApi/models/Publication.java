package libraryProject.restApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Publication {
    private Long id;
    private String title;
    private String author;
    private Integer isbn;
    private Integer pagesQuantity;
    private Integer launchYear;
}
