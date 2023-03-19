package com.kth.board.domain.comment.controller;

import com.kth.board.domain.comment.domain.Comment;
import com.kth.board.domain.comment.dto.CommentSaveDto;
import com.kth.board.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    //
    private final CommentRepository commentRepository;

    @PostMapping("/create")
    public Comment create(@RequestBody CommentSaveDto dto){
        Comment comment = commentRepository.save(dto.toEntity());

        return comment;
    }

    @GetMapping("/findByBoardId/{boardId}")
    public List<Comment> findByBoardId(@PathVariable String boardId){
        return commentRepository.findByBoardId(boardId);
    }

    @DeleteMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable String id){
        Optional<Comment> comment = commentRepository.findById(id);
        commentRepository.delete(comment.get());

        return true;
    }
}
