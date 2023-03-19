package com.kth.board.domain.board.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@Document(collection = "board")
public class Board {
    @Id
    private String _id;
    private String title;
    private String userName;
    private String content;
    private int boardKind;
    private String registeredDate;
    private String userId;
    private byte[] image;
}
