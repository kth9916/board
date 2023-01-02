package com.example.board.domain.board.repository;

import com.example.board.domain.board.domain.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board, String> {
    Board findByTitle(String title);

    List<Board> findByBoardKind(int boardKind);
}
