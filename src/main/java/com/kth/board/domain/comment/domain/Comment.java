package com.kth.board.domain.comment.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
@Data
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;
    private String boardId;
    private String userId;
    private String userName;
    private String commentContent;
    private String registeredDate;
}
