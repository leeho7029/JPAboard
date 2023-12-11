package com.edutech.service;

import com.edutech.dto.BoardDTO;
import com.edutech.entity.Board;
import com.edutech.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@RequiredArgsConstructor는 Lombok라이브러리이고
// 클래스의 final이나 @NonNull이 붙은 필드를 가지고 있는경우 해당 필드를 사용하여 생성자를 자동으로 생성해준다.
@Transactional
public class BoardServiceImpl implements BoardService {
//스프링 버전이 4.3 이상이라면, @Autowired 어노테이션을 생략해도 의존성 주입이 제대로 이루어진다.
    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;

    @Override
    public BoardDTO findByBno(Integer bno) {
        Optional<Board> result = boardRepository.findById(bno);
        BoardDTO dto = modelMapper.map(result, BoardDTO.class);
        return dto;
    }
    //Optional은 자바에서 값이 존재할수도 있고, 존재하지 않을수도 있는 컨테이너 객체
    // optional은 null을 직접 다루는 것을 피하고 값의 존재 여부를 명시적으로 다룰수 있도록 도와준다.
    //Optionadl<Board>는 Board타입의 객체가 존재할수도 존재하지 않을수도 있다는 것, 주로 findById등의 메서드에서 사용되어
    //해당 ID에 해당하는 엔터티를 찾았을 때는 OPtional에 해당 엔터티가 포함되어 반환되고, 찾지 못했을 떄는 Optional이 비어있는 상태로 반환


    @Override
    public List<BoardDTO> findAll() { //board -> BoardDTO.class 끝까지 반복
        List<Board> lst = boardRepository.findAll();
        List<BoardDTO> boardList = lst.stream().map(board
                        -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        return boardList;
        //boardRepository.findAll()은 jpa의 기본 메서드다.
        //**CRUD메서드
        //save(entity): 엔터티를 저장하거나 업데이트
        //findById(ID primaryKey):주어진 id에 해당하는 엔터티를 찾는다.
        //findAll(): 모든 엔터티를 검색한다.
        //deleteById(ID primarykey):주어진 ID에 해당하는 엔터티를 삭제한다.
        //**정렬, 페이징
        //findAll(Sort sort):정렬된 결과를 얻는다.
        //fibdAll(Pageable pageable): 페이징된 결과를 얻는다.
        //페이지 적용하려면 BoardRepository에 Page<Board> findAll(Pageable pageable);
        //서비스나 컨트롤러에서
        //public Page<Board> getAllBoards(int page, int size){
        //Pageable pageable = PageRequest.of(page, size);
        //return boardRepository,findAll(pageale);
        //} 작성하면 된다.

        //List<Board> findByTitleAndWriter(String title, String writer);는 findByTitleAndWriter라는 jpa기본메서드를 사용한건데
        // 만약 Board 엔티티의 필드를 title과 writer로 안하고 다른 이름 title author, titles authors로 title과 writer로 안했으면 사용못함.
        //

        //**count 및 존재여부확인
        //count():엔터티의 총개수를 반환
        //existById(ID primaryKey):주어진 id에 해당하는 엔터티의 존재 여부를 확인한다.
        //**삭제
        //delete(entity):주어진 엔터티를 삭제
        //deleteAll():모든 엔터티를 삭제

        // stream()은 리스트를 스트림으로 변환한다. 스트림은 데이터를 순차적으로 처리할수 있다
        //.map은 map함수를 이용하여 각 요소를 다른 형태로 변환한다. 스트림은 연속된 요소를 처리할수 있는 데이터 처리 추상화다.
        //map(board -> modelMapper.map(board, BoardDTO.class))는
        //map 함수를 사용하여 각 요소를 다른 형태로 변환
        //두번쨰 board는 Board 엔터티
        //modelMapper.map(board, BoardDTO.class)는 modelmapper를 사용하여 Board를 dto로 변환시킴
        //collect(Collectors.toList())의 collect메서드는 스트림의 결과를 수집한다.
        //collectors.toList()는 요소들을 리스트로 수집한다.

        //stream으로 각 요소 변환 ->map으로 변환작업 정의 -> collect로 최종 결과 수집
        //람다 표현식 (매개변수)->{실행코드} 매개변수의 타입은 생략가능
        //스트림은 데이터소스(리스트,배열 등)로부터 데이터를 읽어온다

        //lst라는 Board리스트값을 만든다.
        //lst를 stream()으로 리스트에서 값 추출하고 map으로 다시 묶는데 board형태엿던것을 dto형식으로 바꾸려고
        //(board->modelMapper.map(board, boarddto.class)를 사용한다. board는 stream으로 추출한 값을 약칭한거다
        //modelMapper는 객체간 매핑을 수행하는데 사용돠며, 두 객체 간의 필드복사 및 변환을 자동처리. 매핑이란 한 집합의 각 요소를 다른 집합의 요소로 대응시키는것
        //modelMapper.map(board, boarddto.class)에서 board는 Board의 엔터티 객체이다. modelMapper는 두 객체간의 필드명과 타입이 일치하면 자동으로 매핑해준다. board가 Board의 객체인건 자동으로 인식한다.
        // .collect(Collectors.toList()); 매핑된 결과를 리스트로 저장한다.
        //Collectors.toList()는 collect 메서드와 함꼐 사용되는 Collector의 구현 중 하나이다. collector는 스트림의 요소를 리스트로 수집하는 역할을 한다.
        //collect의 인자로 사용된 collectors.toList()는 스트림의 요소를 담을 새로운 ArrayList생성, 스트림의 각 요소를 생성된 리스트에 추가
        //collect의 인자라는 건 collect 메서드에 전달되는 Collector 객체를 의미, collectors.toList()가 Collector 인터페이스를 구현한 객체를 반환하는데 이것이 collect 메서드의 인자로 사용되어 스트림의 요소를 수집한다.
        //그래서 Collectors.toList()가 collect 메서드의 인자로 사용되었다고 한 것이다.
        //인자란 메서드나 함수에 전달되는 값을 의미한다. 메서드가 호출될떄, 해당 메서드에 필요한 값을 전달하면 그 값이 메서드의 매개변수에 할당되어 사용된다. 이때 전달되는 값을 인자라고 부른다
    }

    @Override
    public Integer register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        Integer bno = boardRepository.save(board).getBno();
        return bno; // boardRepository.save(board)로 등록시킨 게시글의 글번호
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow(); //result값이 비어있는 경우 .orElseThrow();를 사용하여 예외를 발생시킨다.
        board.change(boardDTO.getTitle(), boardDTO.getContent()); //change는 Board 엔터티 내부에 구현된 메서드
        boardRepository.save(board);
    }

    @Override
    public void remove(Integer bno) {
        boardRepository.deleteById(bno);
    }
}
