package com.example.board.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
    Board findByTitle(String title);
}
