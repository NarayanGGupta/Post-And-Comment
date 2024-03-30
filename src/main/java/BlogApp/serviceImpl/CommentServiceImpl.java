package BlogApp.serviceImpl;

import BlogApp.entity.Comment;
import BlogApp.entity.Post;
import BlogApp.payload.CommentDto;
import BlogApp.payload.PostAlongWithAllComments;
import BlogApp.payload.PostDto;
import BlogApp.repository.CommentRepository;
import BlogApp.repository.PostRepository;
import BlogApp.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    private PostRepository postRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto,long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedComment);

        return dto;
    }

    @Override
    public PostAlongWithAllComments getAllCommentsByPostId(long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();

        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        List<Comment> byPostId = commentRepository.findByPostId(postId);
        List<CommentDto> dto = byPostId.stream().map(c -> mapToDto(c)).collect(Collectors.toList());

        PostAlongWithAllComments postAlongWithAllComments = new PostAlongWithAllComments();
        postAlongWithAllComments.setCommentDto(dto);
        postAlongWithAllComments.setPostDto(postDto);

        return postAlongWithAllComments;

    }

    @Override
    public void deleteCommentsById(long id) {
        commentRepository.deleteById(id);
    }

    Comment mapToEntity(CommentDto commentDto){
       Comment comment = modelMapper.map(commentDto, Comment.class);
       return comment;
   }
   CommentDto mapToDto(Comment comment){
       CommentDto dto = modelMapper.map(comment, CommentDto.class);
       return dto;
   }
}
