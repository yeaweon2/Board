package co.java.board.service;

import java.util.List;

import co.java.board.vo.BoardVO;

public interface BoardService {
	
	int insertChk(BoardVO vo);
	
	List<BoardVO> boardSelectList();
	
	BoardVO boardSelect(BoardVO vo);
	
	int boardInsert(BoardVO vo);
	
	int boardUpdate(String id);
	
	int boardDelete(BoardVO vo);	
}
