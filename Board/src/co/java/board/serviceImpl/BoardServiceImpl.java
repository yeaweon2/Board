package co.java.board.serviceImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.java.board.dao.DAO;
import co.java.board.service.BoardService;
import co.java.board.vo.BoardVO;

public class BoardServiceImpl extends DAO implements BoardService {
	PreparedStatement psmt;
	ResultSet rs;
	
	@Override
	public List<BoardVO> boardSelectList() {
		// TODO 글 전체목록 조회 
		List<BoardVO> list = new ArrayList<BoardVO>();
		String sql = "select boardid, writer, title, sysdate , hit from board order by boardid";
		BoardVO vo = null;
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new BoardVO();
				vo.setBoardId(rs.getString("boardid"));
				vo.setWriter(rs.getString("writer"));
				vo.setTitle(rs.getString("title"));
				vo.setEnterDate(rs.getDate("sysdate"));
				vo.setHit(rs.getInt("hit"));
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return list;
	}

	@Override
	public BoardVO boardSelect(BoardVO vo) {
		// TODO 글 상세조회
		BoardVO bvo = null;
		String sql = "select boardid, writer, title, subject , sysdate from board where boardid = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getBoardId());
			rs = psmt.executeQuery();
			if(rs.next()) {
				bvo = new BoardVO();
				bvo.setBoardId(rs.getString("boardid"));
				bvo.setWriter(rs.getString("writer"));
				bvo.setTitle(rs.getString("title"));
				bvo.setSubject(rs.getString("subject"));
				bvo.setEnterDate(rs.getDate("sysdate"));
				boardUpdate(rs.getString("boardid").toString());  // hit수 증가
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bvo;
	}

	@Override
	public int boardInsert(BoardVO vo) {
		// TODO 글작성
		String sql = "insert into board (boardid, writer, title, subject) values (?,?,?,?)";
		int n = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getBoardId());
			psmt.setString(2, vo.getWriter());
			psmt.setString(3, vo.getTitle());
			psmt.setString(4, vo.getSubject());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;			
	}

	@Override
	public int boardUpdate(String id) {
		// TODO 글업데이트
		String sql = "update board set hit = hit+1 where boardid = ?";
		int n = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;		
	}

	@Override
	public int boardDelete(BoardVO vo) {
		// TODO 글삭제
		String sql = "delete from board where boardid = ?";
		int n = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getBoardId());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

	@Override
	public int insertChk(BoardVO vo) {
		// TODO id 중복체크
		int cnt = 0;
		String sql = "select count(*) cnt from board where boardid = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getBoardId());
			rs = psmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}

}
