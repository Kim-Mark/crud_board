package com.crudboard.crud_board.service;

import com.crudboard.crud_board.entity.Board;
import com.crudboard.crud_board.dto.BoardDto;
import com.crudboard.crud_board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardService(){  //

    }

    public BoardService(BoardRepository boardRepository) {  //
        this.boardRepository = boardRepository;
    }



    // 게시글 저장
    public void save(BoardDto boardDto) {
        // DTO를 Entity로 변환
        Board board = new Board();
        board.setBoardWriter(boardDto.getBoardWriter());
        board.setBoardPass(boardDto.getBoardPass());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContents(boardDto.getBoardContents());
        board.setBoardHits(boardDto.getBoardHits());
        //board.setCreatedAt(boardDto.getCreatedAt());
        // 데이터베이스에 저장
        boardRepository.save(board);
    }

    // 모든 게시글 조회
    public List<BoardDto> findAll() {
        List<Board> boardList = boardRepository.findAll();
        // Entity 리스트를 DTO 리스트로 변환
        return boardList.stream()
                .map(board -> new BoardDto(board.getId(), board.getBoardWriter(), board.getBoardPass(), board.getBoardTitle(), board.getBoardContents(), board.getBoardHits(), board.getCreatedAt()))
                .collect(Collectors.toList());
    }

    // 게시글 조회
    public BoardDto findById(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        // 존재하는지 확인 후 DTO로 변환
        Board board = boardOptional.get();
        return new BoardDto(board.getId(), board.getBoardWriter(), board.getBoardPass(), board.getBoardTitle(), board.getBoardContents(), board.getBoardHits(), board.getCreatedAt());
    }

    // 조회수 업데이트
    public void updateHits(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            board.setBoardHits(board.getBoardHits() + 1);
            boardRepository.save(board);
        }
    }

    // 게시글 수정
    public void update(BoardDto boardDto) {
        Optional<Board> boardOptional = boardRepository.findById(boardDto.getId());
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            board.setBoardWriter(boardDto.getBoardWriter());
            board.setBoardPass(boardDto.getBoardPass());
            board.setBoardTitle(boardDto.getBoardTitle());
            board.setBoardContents(boardDto.getBoardContents());
            board.setBoardHits(boardDto.getBoardHits());
            //board.setCreatedAt(boardDto.getCreatedAt());
            boardRepository.save(board);
        }else{
            throw new RuntimeException("Board not found with id" + boardDto.getId());
        }
    }

    // 게시글 삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
