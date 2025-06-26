package com.busanit501.hello_project._3jdbc.service;

import com.busanit501.hello_project._3jdbc.dao.TodoDAO;
import com.busanit501.hello_project._3jdbc.domain.TodoVO;
import com.busanit501.hello_project._3jdbc.dto.TodoDTO;
import com.busanit501.hello_project._3jdbc.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {
    INSTANCE;

    // 다른 기능 불러와서 사용할 준비
    // DB 로직 처리하는 DAO 기능 필요함
    private TodoDAO dao;
    // DTO <-> VO, 변환 해주는 도구 , 모델 맵퍼 기능 필요함
    private ModelMapper modelMapper;

    TodoService() {
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    // 등록기능,
    // 화면에서 데이터 입력 후 전달 -> 컨트롤러에서 데이터 받고, DTO 담기
    // -> DTO를 서비스에 전달 -> 서비스에서 받은 DTO를 VO 변환, 다시 DAO 전달.
    // -> DAO 가 디비 쓰는 과정
    // 현위치 : 서비스,
    // 역할 : 받고, 변환, 전달.
    // 전달 개요 : 화면 -> 컨트롤러(C) -> 서비스 (S):현위치 - > DAO() -> DB
    public void register(TodoDTO todoDTO) throws Exception {
//        System.out.println("TodoService , 화면으로 부터 받은 데이터 확인. todoDTO: " + todoDTO);
        log.info("TodoService , 화면으로 부터 받은 데이터 확인. todoDTO: " + todoDTO);
        // DTO -> VO 로 변환하기, 도구 이용해서.
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
//        System.out.println("TodoService 변환 데이터 확인. todoVO: " + todoVO);
        log.info("TodoService , 화면으로 부터 받은 데이터 확인. todoVO: " + todoVO);
        // 서비스 -> DAO의 기능을 이용하자, 의존하자, 도움받자, 재사용하자
        dao.insert(todoVO);
    }

    // 목록기능, 전체조회.
    public List<TodoDTO> listAll() throws Exception {
        // DAO에서, 디비에서 데이터 전체 조회 기능 있음. 일단, 이용하기.
        List<TodoVO> voList = dao.selectAll();
        log.info("현재 TodoService 작업중.listAll ");
        log.info("데이터 확인 : " + voList);

        // 전
//        List<TodoDTO> dtoListFor = new ArrayList<>();
        // TodoVO -> TodoDTO
//        for (int i = 0; i <voList.size(); i++) {
//            TodoVO todoVO = voList.get(i);
//            TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
//            dtoListFor.add(todoDTO);
//        }

        // 후
        // 병렬 처리
        List<TodoDTO> dtoList = voList.stream().map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    //하나조회.
    // 전달 개요 : 화면 -> 컨트롤러(C) -> 서비스 (S):현위치 - > DAO() -> DB
    // 기능 만들고, -> 단위 테스트 하자.
    public TodoDTO getByTno(Long tno) throws Exception{
        log.info("TodoService : 하나 조회 기능 작업");
        // DAO 로 부터 전달 받은 데이터 타입 , TodoVO
        TodoVO todoVO = dao.selectOne(tno);
        //받은 VO -> DTO 변환하기.
        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
        return todoDTO;
    }

    // 삭제 기능.
    public void remove(Long tno) throws Exception{
        log.info("서비스의 삭제 기능 , tno 번호 확인 : " + tno);
        dao.deleteOne(tno);
    }

    // 수정 기능
    public void modify(TodoDTO todoDTO) throws Exception {
        log.info("TodoService 작업중. 수정 작업");
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        dao.updateOne(todoVO);
    }
}
