package BlogApp.controller;

import BlogApp.payload.PostDto;
import BlogApp.payload.PostDtoList;
import BlogApp.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    // http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/posts/2
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Is Deleted!!",HttpStatus.OK);
    }

    // http://localhost:8080/api/posts?pageNo=0&pageSize=2&sortBy=id&sortDir=desc
    @GetMapping
    public ResponseEntity<PostDtoList> getAllPosts(
            @RequestParam (name = "pageNo",defaultValue = "0",required = false)int pageNo,
            @RequestParam (name = "pageSize",defaultValue = "2",required = false)int pageSize,
            @RequestParam (name = "sortBy",defaultValue = "id",required = false)String sortBy,
            @RequestParam (name = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        PostDtoList postDtoList = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePostById(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto postDto = postService.getPostById(id);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
}
