package com.example.board.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
