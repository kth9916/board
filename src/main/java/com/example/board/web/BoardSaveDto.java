package com.example.board.web;

import com.example.board.domain.Board;
import lombok.Data;

@Data
public class BoardSaveDto {
    //
    private String title;
    private String content;
    private int boardNo;

    public Board toEntity(){
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setBoardNo(boardNo);
        return board;
    }
}
