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

    // 생성자
    @Autowired // spring이 boardservice 객체를 자동으로 주입하도록
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 1. 게시글 작성 화면 띄우기
    @GetMapping("/save")
    public String save(){
        return "save"; // save html 띄워주기
    }

    // 2. 글을 다 작성했으면 Controller로 data 넘겨주기
    @PostMapping("/save") // http post요청이 save로 들어올떄
    public String save(@ModelAttribute BoardDto boardDto){ //html폼에서 전달된 data를 boardDto 객체에 매핑
        // 파라미터로 정의한 BoardDto클래스를 가지는 매개변수를 객체로 받아서
        // html에서 전달한 data와 dto의 필드 이름이 동일한지 확인하고 setter가 있으면
        // 자동으로 그 값을 boardDto에 담아줌
        System.out.println("BoardDto = " + boardDto);
        boardService.save(boardDto);//서비스 레이어를 통해 db에 게시글 저장
        // 게시글을 작성했으면 다시 목록으로 갈 거임 - redirect 활용
        return "redirect:/list";
    }

    // 3. 게시글 목록 출력 - DB에서 조회한 data를 화면으로 가져와야하기 때문에 Model 사용
    // Model 객체는 Springframework에서 제공하는 인터페이스
    // db에서 data 가져올 때는 Model 사용
    @GetMapping("/list")
    public String findAll(Model model){//Model 객체를 사용해서 controller에서 뷰로 데이터 전달 
        List<BoardDto> boardDtoList = boardService.findAll(); //서비스 레이어를 통해 모든 게시글 가져오기
        model.addAttribute("boardList", boardDtoList); // 가져온 게시글 목록을 model에 추가하여 뷰에 전달
        System.out.println("boardDto list: " + boardDtoList);
        return "list";
    }

    // 4. 게시글 조회 클래스 구현
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){//URL 경로에서 id 값을 추출하여 메소드 파라미터로 전달합니다.
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
    public String update(@PathVariable("id") Long id, Model model) throws IllegalAccessException {
        BoardDto boardDto = boardService.findById(id);
        if(boardDto == null){
            throw new IllegalAccessException("Invalid board ID: " + id);
        }
        model.addAttribute("board", boardDto);
        return "update";
    }

    // 2. 수정 요청 받아주는 메소드, 수정 후 업데이트가 끝나면 수정된 결과가 나타나는 상세화면 다시 출력
    // 수정 후 data가 새로 업데이트 됨. db에서 그 data를 다시 꺼냐오는 과정
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, BoardDto boardDto, Model model){
        boardDto.setId(id);
        boardService.update(boardDto);
        BoardDto dto = boardService.findById(boardDto.getId()); // 수정된 게시글 다시 조회
        model.addAttribute("board", dto);// 수정된 게시글 data를 모델에 추가하여 뷰에 전달
        return "detail";
    }

    // 6. 게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.delete(id);
        return "redirect:/list";
    }



}
