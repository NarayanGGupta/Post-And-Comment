package BlogApp.service;

import BlogApp.payload.PostDto;
import BlogApp.payload.PostDtoList;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    void deletePostById(long id);

    PostDtoList getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePostById(long id, PostDto postDto);

    PostDto getPostById(long id);
}
