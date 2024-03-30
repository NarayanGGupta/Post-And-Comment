package BlogApp.controller;

import BlogApp.payload.CommentDto;
import BlogApp.payload.PostAlongWithAllComments;
import BlogApp.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    //http://localhost:8080/api/comments/1
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable long postId){
        CommentDto dto = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/comments/1
    @GetMapping("/{postId}")
    public ResponseEntity<PostAlongWithAllComments> getAllCommentsByPostId(@PathVariable long postId){
        PostAlongWithAllComments postAlongWithAllComments = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(postAlongWithAllComments,HttpStatus.OK);
    }

    // http://localhost:8080/api/comments/4
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentsById(@PathVariable long id){
        commentService.deleteCommentsById(id);
        return new ResponseEntity<>("Comment Is Deleted!!",HttpStatus.OK);
    }
}
