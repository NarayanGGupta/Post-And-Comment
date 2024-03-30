package BlogApp.service;

import BlogApp.payload.CommentDto;
import BlogApp.payload.PostAlongWithAllComments;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto, long postId);

    PostAlongWithAllComments getAllCommentsByPostId(long postId);

    void deleteCommentsById(long id);
}
