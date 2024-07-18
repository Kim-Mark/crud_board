package com.crudboard.crud_board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 글 번호
    private String boardWriter; // 작성자
    private String boardPass; // 게시글 비밀번호
    private String boardTitle; // 제목
    private String boardContents; // 내용
    private int boardHits; // 조회수
    private String createdAt; // 작성시간

    // 기본 생성자
    public Board() {}

    // 생성자
    public Board(Long id, String boardWriter, String boardPass, String boardTitle, String boardContents, int boardHits, String createdAt) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.createdAt = createdAt;
    }

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoardWriter() {
        return boardWriter;
    }

    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }

    public String getBoardPass() {
        return boardPass;
    }

    public void setBoardPass(String boardPass) {
        this.boardPass = boardPass;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContents() {
        return boardContents;
    }

    public void setBoardContents(String boardContents) {
        this.boardContents = boardContents;
    }

    public int getBoardHits() {
        return boardHits;
    }

    public void setBoardHits(int boardHits) {
        this.boardHits = boardHits;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
