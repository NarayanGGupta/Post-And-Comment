package BlogApp.payload;

import lombok.Data;

import java.util.List;

@Data
public class PostDtoList {
    private List<PostDto> postDto;
    private int totalPages;
    private int totalElements;
    private boolean firstPage;
    private boolean lastPage;
    private int pageNumber;
}
