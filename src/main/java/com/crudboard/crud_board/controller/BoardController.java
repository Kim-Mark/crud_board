package com.crudboard.crud_board.controller;

import com.crudboard.crud_board.dto.BoardDto;
import com.crudboard.crud_board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService; // Controller는 Service를 참조함.

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 1. 게시글 작성 화면 띄우기
    @GetMapping("/save")
    public String save(){
        return "save"; // save html 띄워주기
    }

    // 2. 글을 다 작성했으면 Controller로 data 넘겨주기
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto){
        // 파라미터로 정의한 BoardDto클래스를 가지는 매개변수를 객체로 받아서
        // html에서 전달한 data와 dto의 필드 이름이 동일한지 확인하고 setter가 있으면
        // 자동으로 그 값을 boardDto에 담아줌
        System.out.println("BoardDto = " + boardDto);
        boardService.save(boardDto);
        // 게시글을 작성했으면 다시 목록으로 갈 거임 - redirect 활용
        return "redirect:/list";
    }

    // 3. 게시글 목록 출력 - DB에서 조회한 data를 화면으로 가져와야하기 때문에 Model 사용
    // Model 객체는 Springframework에서 제공하는 인터페이스
    // db에서 data 가져올 때는 Model 사용
    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDto> boardDtoList = boardService.findAll();
        model.addAttribute("boardList", boardDtoList);
        System.out.println("boardDto list: " + boardDtoList);
        return "list";
    }

    // 4. 게시글 조회 클래스 구현
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        // 조회수 처리
        boardService.updateHits(id);

        // 상세내용 가져오기
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);// 가져온 data를 model에 담아서 detail html에 집어 넣겠다
        System.out.println("BoardDto = " + boardDto);
        return "detail";

    }

    // 5. 게시글 수정
    // 1. 화면에 수정할 게시글 한 번 나오게 하고
    // 2. 수정하고, 수정 버튼 클릭하면 반영

    // 1. 화면에 수정할 게시글 db 에서 한 번 받아오기
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);
        return "update";
    }

    // 2. 수정 요청 받아주는 메소드, 수정 후 업데이트가 끝나면 수정된 결과가 나타나는 상세화면 다시 출력
    // 수정 후 data가 새로 업데이트 됨. db에서 그 data를 다시 꺼냐오는 과정
    @PostMapping("/update/{id}")
    public String update(BoardDto boardDto, Model model){
        boardService.update(boardDto);
        BoardDto dto = boardService.findById(boardDto.getId());
        model.addAttribute("board", dto);
        return "detail";
    }

    // 6. 게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.delete(id);
        return "redirect:/list";
    }



}
