package BlogApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAlongWithAllComments {
    private PostDto postDto;
    private List<CommentDto> commentDto = new ArrayList<>();
}
