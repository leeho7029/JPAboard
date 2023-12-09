package com.edutech.controller;

import com.edutech.dto.BoardDTO;
import com.edutech.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/list")
    @ResponseBody
    public List<BoardDTO> listAll(){
        List<BoardDTO> boardList = boardService.findAll();
        return boardList;
    }

    @GetMapping("/api/read")
    @ResponseBody
    public BoardDTO findByBno(Integer bno){
        BoardDTO board = boardService.findByBno(bno);
        return board;
    }

    @PostMapping("/api/register")
    @ResponseBody
    public Integer apiboardWrite(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("board POST register.......");
        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
        }
        Integer bno  = boardService.register(boardDTO);
        return bno;
    }



    @PostMapping("/api/modify")
    @ResponseBody
    public BoardDTO modifyPro(@Valid BoardDTO boardDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        log.info("board modify post......." + boardDTO);
        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            String link = "bno="+boardDTO.getBno();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return boardDTO;
        }
        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return boardDTO;
    }
    //@Valid는 BoardDTO 객체에 대한 검증을 수행하라는 표시. 검증은 BoardDTO클래스 내에 지정된 검증 어노테이션에 따라 진행.- @Valid는 Bean Validation API를 통해 유효성검증이 수행된다.
    // ㄴ> BoardDTO클래스에 지정된 @NotEmpty와 @Size 어노테이션은 Bean Validation API의 일부로서 객체의 필드에대한 검증규칙 정의. @Valid 어노테이션을 사용하면 이러한 검증 규칙이 활성화되어 해당 객체가 유효한지 여부 확인.
    // @NotNull,@Min,@Max,@Email(문자열이 이메일주소형식을 따르는지 확인),@Pattern(regexp="^[a-zA-z0-9]*$"),@Future,@Past(날짜 필드의 값이 미래또는 과거인지 확인),@NotBlank(문자열 필드가 null이 아니고 trim한 결과가 비어있지 않은지 확인)
    //BindingResult는 검증 결과를 저장하는 객체로 검증 중 발생한 오류 정보를 담고 있다.
    //RedirectAttributes는 리다이렉트 시 속성을 전달하기위한 객체로, 리다이렉트된 페이지에서 사용할수 있는 속성을 추가할수 있다.
    //redirectAttributes.addFlashAttribute-검증 오류정보를 리다이렉트시 플래시 속성으로 추가. 플래시 송성은 한번의 리다이렉트에만 유효하며, 다음페이지에서 속성을 사용할수있다.
    // addFlashAttribute를 사용하여 데이터를 전달하면 해당 데이터는 url에 노출안되지만 일회성으로 전달됨. 전달되면 더이상 그 데이터는 유효하지 않음.전달된 페이지에서 다른 페이지로 전달못함
    //addAttribute는 url에 노출되어 다음 요청으로 전달되고 전달된 페이지에서 다른 페이지로 이동할때 전달될수 있음.
    //리다이렉트는 클라이언트의 요청을 받아 처리한후 특정페이지로 강제적으로 이동시키는 것. 주로 사용자가 어떤 액션을 수행한 후에 그 결과를 보여주거나 성공/실패 메시지 등을 표시하는데 활용된다.

    @PostMapping("/api/remove")
    public String remove(Integer bno, RedirectAttributes redirectAttributes) {
        log.info("remove post.. " + bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/apiboard/list";
    }

    @GetMapping("/board/list")
    public String boardListAll(Model model){
        List<BoardDTO> boardList = boardService.findAll();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/board/read")
    public String boardFindByBno(Integer bno, Model model){
        BoardDTO board = boardService.findByBno(bno);
        model.addAttribute("dto", board);
        return "board/read";
    }

    @GetMapping("/board/write")
    public String boardForm(){
        return "board/write";
    }

    @PostMapping("/board/register")
    public String boardWrite(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("board POST register.......");
        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
        }
        Integer bno  = boardService.register(boardDTO);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify")
    public String boardModify(Integer bno, Model model){
        BoardDTO boardDTO = boardService.findByBno(bno);
        model.addAttribute("dto", boardDTO);
        return "board/modify";
    }

    @PostMapping("/board/modify")
    public BoardDTO boardModifyPro(@Valid BoardDTO boardDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
        log.info("board modify post......." + boardDTO);
        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            String link = "bno="+boardDTO.getBno();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return boardDTO;
        }
        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return boardDTO;
    }

    @PostMapping("/board/remove")
    public String boardRemove(Integer bno, RedirectAttributes redirectAttributes) {
        log.info("remove post.. " + bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }
}
