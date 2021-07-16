package co.java.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import co.java.board.service.BoardService;
import co.java.board.serviceImpl.BoardServiceImpl;
import co.java.board.vo.BoardVO;

public class Menu {
	private BoardService dao = new BoardServiceImpl();
	private Scanner scn = new Scanner(System.in);
	
	public void run() {
		mainMenu();
	}
	
	private void mainMenu() {

		boolean chk = true;
		int selNo = 0; 
		
		while(chk) {
			menuTitle();
			selNo = scanInt("【 번호선택 】 ==> ");
			switch(selNo) {
				case 1 : boardDetail(); break;
				case 2 : boardInsert(); break;
				case 3 : boardDelete(); break;
				case 4 : 
					chk = false; 
					System.out.println();
					System.out.println("──────────────────────【 Good Bye !! 】──────────────────────"); 
					break;
			}	
		}
		
	}
	
	private void boardDelete() {
		// TODO 정보삭제
		BoardVO vo = new BoardVO();
		
		String id = scanString("◈ 삭제할 ID 입력 ==> ");
		vo.setBoardId(id);
		
		int chkNo = dao.boardDelete(vo);
		if(chkNo > 0) {
			System.out.println("※ 삭제되었습니다.");
		}else {
			System.out.println("※ 삭제할 글이 없습니다. ");
		}
	}

	private void boardInsert() {
		// TODO 정보입력
		BoardVO vo = new BoardVO();
		
		String id = scanString("◈ ID 입력 ==> ");
		String writer = scanString("◈ 작성자 입력 ==> ");
		String title = scanString("◈ 글제목 입력 ==> ");
		String subject = scanString("◈ 내용 입력 ==> ");

		vo.setBoardId(id);
		vo.setWriter(writer);
		vo.setTitle(title);
		vo.setSubject(subject);
		
		// ID 중복체크
		int duplChk = dao.insertChk(vo);

		if(duplChk > 0 ) {
			System.out.println("※ 중복된 ID가 존재합니다. 다시 입력해주세요.");
		}else {
			int chkNo = dao.boardInsert(vo);
			if(chkNo > 0) {
				System.out.println("※ [ " + vo.getTitle() + " ] 입력되었습니다.");
			}else {
				System.out.println("※ 입력시 오류가 발생하였습니다. [ 실패 ]");
			}				
		}
	}

	private void boardDetail() {
		// TODO 상세조회
		BoardVO vo = new BoardVO();		
		String id = scanString("◈ 조회할 글ID 입력 ==> ");
		
		vo.setBoardId(id);
		vo = dao.boardSelect(vo);
		if(vo != null) {
			detailView(vo);
		}else{
			System.out.println();
			System.out.println("※ 해당 글은 존재하지 않습니다.");
		}
	}

	private void menuTitle() {
		System.out.println();
		System.out.println("───────────────────────【 전체 LIST 】───────────────────────");
		boardList();
		System.out.println("───────────────────────────────────────────────────────────");
		System.out.println("   1.글조회   │   2.글작성   │   3.글삭제   │   4.종료"     );
		System.out.println("───────────────────────────────────────────────────────────");
	}
	
	private void boardList() {
		List<BoardVO> list = new ArrayList<>();
		list = dao.boardSelectList();
		for(BoardVO vo : list) {
			vo.toString();
		}
	}
	
	private void detailView(BoardVO vo) {
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.println(" ◇ 글제목 : " + vo.getTitle());
		System.out.println(" ◇ 작성자 : " + vo.getWriter());
		System.out.println(" ◇ 내 용 : " + vo.getSubject());
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	}
	
	private String scanString(String txt) {
		System.out.print(txt);
		String msg = scn.nextLine();
		return msg;
	}
	
	private int scanInt(String txt) {
		System.out.print(txt);
		int msg = 0;
		boolean chk = true;
		while(chk) {
			try {
				msg = scn.nextInt();
				scn.nextLine();
				chk = false;
			}catch(Exception e) {
				System.out.print("◈ 다시 입력 (숫자만) ==> ");
				scn.nextLine();
			}
		}
		return msg;
	}	
	
	
}
