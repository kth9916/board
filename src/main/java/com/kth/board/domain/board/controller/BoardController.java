package com.kth.board.domain.board.controller;

import com.kth.board.domain.board.domain.Board;
import com.kth.board.domain.board.repository.BoardRepository;
import com.kth.board.domain.board.dto.BoardSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.kth.board.domain.board.dto.BoardSaveDto.isImageFile;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    //DI
    private final BoardRepository boardRepository;

    @PutMapping("/modifyById/{id}")
    public void update(@RequestBody BoardSaveDto dto, @PathVariable String id) throws IOException {
        Board board = dto.toEntity();
        board.set_id(id);

        boardRepository.save(board);
    }

    @DeleteMapping("/deleteById/{id}")
    public int deleteById(@PathVariable String id){
        boardRepository.deleteById(id);

        return 1;
    }

    @GetMapping("/findById/{id}")
    public Board findById(@PathVariable String id){
        return boardRepository.findById(id).get();
    }

    @GetMapping("/findAll")
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    @GetMapping(value = "/findByBoardKind/{boardKind}")
    public List<Board> findByBoardKind(@PathVariable int boardKind){return boardRepository.findByBoardKind(boardKind);}

    @GetMapping("/findByTitle/{title}")
    public Board findByTitle(@PathVariable String title){
        return boardRepository.findByTitle(title);
    }

    @PostMapping("/registerPost")
    public Board save(@RequestParam("title") String title,
                      @RequestParam("content") String content,
                      @RequestParam("boardKind") int boardKind,
                      @RequestParam(value = "image", required = false) MultipartFile image,
                      @RequestParam("userName") String userName,
                      @RequestParam("userId") String userId) throws IOException {   // {"title:"제목3", "content":"내용3"} 타입으로 데이터를 받도록 @RequestBody 어노테이션을 사용한다.

        //
        if(image != null && !isImageFile(image)){
            throw new IllegalArgumentException("올바른 이미지 파일이 아닙니다.");
        }
        BoardSaveDto dto = new BoardSaveDto(title, content, boardKind,userName, userId, image);
        return boardRepository.save(dto.toEntity()); // MessageConverter 발동  -> 자바 오브젝트를 Json 변환해서 응답함.
    }

    @GetMapping("/findByUserIdAndBoardKind/{userId}/{boardKind}")
    public List<Board> findByUserIdAndBoardKind(@PathVariable String userId, @PathVariable int boardKind){
        return boardRepository.findByUserIdAndBoardKind(userId, boardKind);
    }

    @GetMapping("/findByUserId/{userId}")
    public List<Board> findByUserId(@PathVariable String userId){
        return boardRepository.findByUserId(userId);
    }

    @GetMapping("/findById/{id}/image")
    public byte[] findImageById(@PathVariable String id){return boardRepository.findById(id).get().getImage();}
}
