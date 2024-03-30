package BlogApp.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private long id;
    private String name;
    private String message;
    private LocalDateTime dateTime;
}
