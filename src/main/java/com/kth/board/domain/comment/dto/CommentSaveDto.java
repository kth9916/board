package com.kth.board.domain.comment.dto;

import com.kth.board.domain.comment.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CommentSaveDto {
    //
    private String boardId;
    private String userId;
    private String userName;
    private String commentContent;
    private String registeredDate;

    public Comment toEntity(){
        Comment comment = new Comment();
        comment.setBoardId(boardId);
        comment.setUserId(userId);
        comment.setUserName(userName);
        comment.setCommentContent(commentContent);

        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now();
        // 포맷팅
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        comment.setRegisteredDate(formatedNow);
        return comment;
    }
}
