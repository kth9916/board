package com.kth.board.domain.comment.repository;

import com.kth.board.domain.comment.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    //
    List<Comment> findByBoardId(String boardId);
}
