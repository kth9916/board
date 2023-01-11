package com.example.board.domain.board.dto;

import com.example.board.domain.board.domain.Board;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BoardSaveDto {
    //
    private String title;
    private String content;
    private int boardKind;
    private String registeredDate;
    private String userName;

    private String userId;

    public Board toEntity(){
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setBoardKind(boardKind);
        board.setUserName(userName);
        board.setUserId(userId);

        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now();
        // 포맷팅
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        board.setRegisteredDate(formatedNow);
        return board;
    }
}
