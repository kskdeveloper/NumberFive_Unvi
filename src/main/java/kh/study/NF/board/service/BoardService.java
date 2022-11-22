//by 유빈
package kh.study.NF.board.service;
import java.util.List;

import kh.study.NF.board.vo.BoardCategoryVO;
import kh.study.NF.board.vo.BoardVO;
import kh.study.NF.board.vo.ReplyVO;

public interface BoardService {
	
//--------------- [ 공통 게시판 ] -----------------------------//
	// 글등록
	void insertBoard(BoardVO boardVO);
	
	// 글 목록조회
	List<BoardVO> selectBoard();
	// 목록조회(Board 소스)
	List<BoardVO> selectBoardList(BoardVO boardVO);
	// 상세조회
	BoardVO selectDetailBoard(String boardNo);
	//조회수증가
	void updateReadCnt(String boardNo);
	// 수정
	void update(BoardVO boardVO);
	// 삭제
	void delete(String boardNo);
	//게시글 총 개수 조회
	int selectBoardCnt();
//--------------- [ 관리자 모드 게시판 ] -----------------------------//
	//카테고리목록조회
	List<BoardCategoryVO> selectBoardCate();
	//카테고리 사용중인 목록조회
	List<BoardCategoryVO> selectBoardCateUse();
	//카테고리등록
	void insertBoardCate(BoardCategoryVO boardCategoryVO);
//--------------- [ 게시판 댓글  ] -----------------------------//
	//댓글 등록(int 대신 void 사용)
	void insertReply(ReplyVO replyVO);
	//댓글 목록조회
	List<ReplyVO> selectReplyList(String boardNo);
	//댓글 삭제
	void deleteReply(int replyNo);
	//????댓글 조회수 증가
	//void updateReadCnt(int replyNo);
	//댓글수정
	void updateReply(ReplyVO replyVO);
	//댓글상세조회
	ReplyVO selectDetailReply(int replyNo);
}
