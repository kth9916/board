package com.example.board.web;

import com.example.board.domain.Board;
import com.example.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    //DI
    private final BoardRepository boardRepository;

    @PutMapping("board/modifyById/{id}")
    public void update(@RequestBody BoardSaveDto dto, @PathVariable String id){
        Board board = dto.toEntity();
        board.set_id(id);

        boardRepository.save(board);
    }

    @DeleteMapping("board/deleteById/{id}")
    public int deleteById(@PathVariable String id){
        boardRepository.deleteById(id);

        return 1;
    }

    @GetMapping("/board/findById/{id}")
    public Board findById(@PathVariable String id){
        return boardRepository.findById(id).get();
    }

    @GetMapping("/board/findAll")
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    @GetMapping("/board/findByBoardKind/{boardKind}")
    public List<Board> findByBoardKind(@PathVariable int boardKind){return boardRepository.findByBoardKind(boardKind);}

    @GetMapping("/board/findByTitle/{title}")
    public Board findByTitle(@PathVariable String title){
        return boardRepository.findByTitle(title);
    }

    @PostMapping("/board/registerPost")
    public Board save(@RequestBody BoardSaveDto dto){   // {"title:"제목3", "content":"내용3"} 타입으로 데이터를 받도록 @RequestBody 어노테이션을 사용한다.
        Board boardEntity = boardRepository.save(dto.toEntity());

        return boardEntity; // MessageConverter 발동  -> 자바 오브젝트를 Json 변환해서 응답함.
    }
}
