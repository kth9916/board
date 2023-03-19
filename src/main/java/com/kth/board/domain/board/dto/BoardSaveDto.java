package com.kth.board.domain.board.dto;

import com.kth.board.domain.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@AllArgsConstructor
public class BoardSaveDto {
    //
    private String title;
    private String content;
    private int boardKind;
    private String userName;

    private String userId;
    private MultipartFile image;

    public Board toEntity() throws IOException {
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setBoardKind(boardKind);
        board.setUserName(userName);
        board.setUserId(userId);
        board.setImage(image.getBytes());

        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now();
        // 포맷팅
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        board.setRegisteredDate(formatedNow);
        return board;
    }

    public static boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
