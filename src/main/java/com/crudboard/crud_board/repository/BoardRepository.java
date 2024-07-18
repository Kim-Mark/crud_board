package com.crudboard.crud_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crudboard.crud_board.entity.Board;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

// save, findById, findAll - 자동제공되는 CRUD 메서드
}
