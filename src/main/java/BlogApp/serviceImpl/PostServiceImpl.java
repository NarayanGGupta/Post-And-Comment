package BlogApp.serviceImpl;

import BlogApp.entity.Post;
import BlogApp.payload.PostDto;
import BlogApp.payload.PostDtoList;
import BlogApp.repository.PostRepository;
import BlogApp.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Optional<Post> byId = postRepository.findById(id);
        if(byId.isPresent()){
            postRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Post Not Found To Be Deleted With PostId :" +id);
        }
    }

    @Override
    public PostDtoList getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> post = all.getContent();
        List<PostDto> postDto = post.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        PostDtoList postDtoList = new PostDtoList();

        postDtoList.setPostDto(postDto);
        postDtoList.setTotalPages(all.getTotalPages());
        postDtoList.setTotalElements((int) all.getTotalElements());
        postDtoList.setFirstPage(all.isFirst());
        postDtoList.setLastPage(all.isLast());
        postDtoList.setPageNumber(all.getNumber());

        return postDtoList;
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.get();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public PostDto getPostById(long id) {
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.orElseThrow(() -> new RuntimeException("Post Not Found With Id :" + id));
        return mapToDto(post);
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
}
