package BlogApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    @Size(min = 4,message = "Title Should Be Atleast 4 Characters")
    private String title;
    @NotEmpty
    private String description;
    private String content;
}
