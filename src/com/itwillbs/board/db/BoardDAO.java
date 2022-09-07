package com.itwillbs.board.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	// DAO (Data Access Object) : 데이터 처리 객체
	
	// 공통변수 (인스턴스 변수)
	private Connection con = null;   // 디비 연결정보 저장 객체
	private PreparedStatement pstmt = null; //디비에 SQL 실행 처리 객체
	private ResultSet rs = null;	//select 실행 결과 저장 객체
	private String sql = "";		// SQL쿼리 구문 저장
	
	public BoardDAO() {
		System.out.println(" DAO : DB연결에 관한 모든정보를 준비 완료! ");
	}
	
	
	// 디비 연결
//	private Connection getConnect() throws Exception{
//		// 디비 연결정보
//		String DRIVER = "com.mysql.cj.jdbc.Driver";
//		String DBURL = "jdbc:mysql://localhost:3306/jspdb";
//		String DBID = "root";
//		String DBPW = "1234";
//		
//		// 1. 드라이버로드
//		Class.forName(DRIVER);
//		System.out.println(" 드라이버로드 성공 ");
//		// 2. 디비연결
//		con = DriverManager.getConnection(DBURL, DBID, DBPW);
//		System.out.println(" 디비연결 성공 ");
//		System.out.println(" con : " + con);
//		
//		return con;
//	}
	// 디비 연결
	
	// 디비 연결 시작
	private Connection getConnect() throws Exception{
		// 디비 연결정보 -context.xml
	
		// 프로젝트 정보를 초기화
		Context initCTX = new InitialContext();
		
		//초기화된 프로젝트 정보 중 데이터 불러오기
	    DataSource ds 
	           = (DataSource)initCTX.lookup("java:comp/env/jdbc/model2");
	    //                                   ------------- 여기까지 고정, 그 후부터는 context.xml에 있는 이름
	    
	    // 연결된 정보를 바탕으로 connection 정보를 리턴
	    con = ds.getConnection();
		
		return con;
	}
	// 디비 연결 끝
	
	
	// 자원 해제
	public void closeDB(){
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
			
			System.out.println(" DAO : 자원해제 성공! ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 자원 해제
	
	
	// 글쓰기 - boardWrite()
	public void boardWrite(BoardDTO dto){
		System.out.println("\n DAO : boardWrite(BoardDTO dto) 호출 ");
		int bno = 0;  //글번호 저장 
		
		try {
			//1.드라이버로드
			//2.디비 연결
			con = getConnect();
			//3. sql 작성 & pstmt 객체
			//  게시판 글번호(bno) 계산 (작성된 가장 마지막글번호 + 1)			
			sql = "select max(bno) from itwill_board";
			pstmt = con.prepareStatement(sql);
			//4. sql 실행
			rs = pstmt.executeQuery();
			
			// * 워크벤치 select 결과
			//  - 삼각형 커서가 있을경우  rs.next() == true
			//  - 원 커서가 있을 경우 rs.next() == false
			//  - 커서가 없을경우  rs.next() == false
			
			// 5. 데이터 처리 (글번호 계산:마지막글번호 + 1)
			if(rs.next()){
				// getInt() => 컬럼의 값을 리턴, 만약에 값이 sql-null경우 0 리턴
				//bno = rs.getInt("bno")+1;(x)
				//bno = rs.getInt("max(bno)")+1;
				bno = rs.getInt(1)+1;
			}
			
			System.out.println(" DAO : 글번호 bno : "+bno);
			
			// 게시판 글 쓰기
			// 3. sql작성 & pstmt 객체
			sql = "insert into itwill_board(bno,name,pass,subject,content,"
					+ "readcount,re_ref,re_lev,re_seq,date,ip,file) "
					+ "values(?,?,?,?,?,?,?,?,?,now(),?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			// ???
			pstmt.setInt(1, bno);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getPass());
			pstmt.setString(4, dto.getSubject());
			pstmt.setString(5, dto.getContent());
			
			pstmt.setInt(6, 0); // 조회수 항상 0 
			pstmt.setInt(7, bno); // 답글 그룹번호 == 글번호 (일반글)
			pstmt.setInt(8, 0); // 답글 레벨 0 (일반글)
			pstmt.setInt(9, 0); // 답글 순서 0 (일반글)
			
			pstmt.setString(10, dto.getIp());
			pstmt.setString(11, dto.getFile());
			
			// 4. SQL 실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 글 작성 완료! ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}
	// 글쓰기 - boardWrite()
	
	// 글 목록 조회(all) - getBoardList()
	public List<BoardDTO> getBoardList(){
		System.out.println("\n DAO : getBoardList() 호출");
		
		
		// 글정보 모두를 저장하는 배열(가변길이)
//		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			// 1. 드라이버로드
			// 2. 디비연결
			con = getConnect();
			// 3. sql 작성 & pstmt 객체
			sql = "select * from itwill_board";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			while(rs.next()){
				// 데이터 있을때 DB에 저장된 정보를 DTO저장 -> List 저장
				
				// DB -> DTO 저장
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("date"));
				dto.setFile(rs.getString("file"));
				dto.setIp(rs.getString("ip"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setSubject(rs.getString("subject"));
				
				// DTO-> List
				boardList.add(dto);
				
			}// while문 끝
			
			System.out.println(" C : 게시판 목록 모두 저장완료 ");
			// System.out.println(" C : "+boardList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		
		return boardList;
	}
	// 글 목록 조회(all) - getBoardList()
	
	       // 글 목록 조회 - getBoardList(int StartRow, int pageSize)
	     	public List<BoardDTO> getBoardList(int StartRow, int pageSize){
			System.out.println("\n DAO : getBoardList() 호출");
			
			
			// 글정보 모두를 저장하는 배열(가변길이)
            //ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
			List<BoardDTO> boardList = new ArrayList<BoardDTO>();
			
			try {
				// 1. 드라이버로드
				// 2. 디비연결
				con = getConnect();
				// 3. sql 작성 & pstmt 객체
				// sql = "select * from itwill_board"; (X) -> 모든 데이터를 가져오는 구문
				
				// limit 시작행-1, 개수 => 시작 지점부터 해당 개수만큼 잘라오기
				// ( 시작행: 5번 글부터 보고 싶다면 4라고 써야함 ) 
				// 정렬 re_ref 내림차순, re_seq 오름차순
				
				sql = "select * from itwill_board "
						+ "order by re_ref desc, re_seq asc "
						+ "limit ?,?";
				
				pstmt = con.prepareStatement(sql);
				
				// ????
				pstmt.setInt(1, StartRow-1); // 시작행 - 1
				pstmt.setInt(2, pageSize);
				
				
				// 4. sql 실행
				rs = pstmt.executeQuery();
				
				// 5. 데이터 처리
				while(rs.next()){
					// 데이터 있을때 DB에 저장된 정보를 DTO저장 -> List 저장
					
					// DB -> DTO 저장
					BoardDTO dto = new BoardDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setContent(rs.getString("content"));
					dto.setDate(rs.getDate("date"));
					dto.setFile(rs.getString("file"));
					dto.setIp(rs.getString("ip"));
					dto.setName(rs.getString("name"));
					dto.setPass(rs.getString("pass"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_seq(rs.getInt("re_seq"));
					dto.setReadcount(rs.getInt("readcount"));
					dto.setSubject(rs.getString("subject"));
					
					// DTO-> List
					boardList.add(dto);
					
				}// while문 끝
				
				System.out.println(" C : 게시판 목록 모두 저장완료 ");
				// System.out.println(" C : "+boardList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			
			
			return boardList;
		}
	     // 글 목록 조회 - getBoardList(int StartRow, int pageSize)
	
	     	
	// 글 개수 조회(all) - getBoardCount()
	public int getBoardCount() {
		System.out.println("\n DAO : getBoardCount() 실행");
		int cnt = 0;

		 
		try {
			// 1.2. 디비 연결 (커넥션풀)
			getConnect();
			// 3. sql 작성 & pstmt 객체
			sql="select count(*) from itwill_board";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()){
				// 데이터 있을 때
			    // cnt = rs.getInt("count(*)");
				 cnt = rs.getInt(1); // 1번 인덱스
				
			}
			
			System.out.println(" DAO : 글 개수 - 총 : "+cnt+"개");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	
		
		
		return cnt;
		
	}
	
	// 글 개수 조회(all) - getBoardCount()
	
	// 글 조회수 1 증가 - updateReadcount()
	
	public void updateReadcount(int bno){
		System.out.println(" C : updateReadcount(int bno) 호출");
		
		
		try {
			//1.2. 디비연결
			con = getConnect();
			
			//3. sql 작성 & pstmt 객체
			sql="update itwill_board set readcount=readcount+1 where bno = ?";
			pstmt = con.prepareStatement(sql);
			
			//???
			pstmt.setInt(1, bno);
			//4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 게시판 글 조회수 1 증가 완료! ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeDB();
		}
		
	}
	
	// 글 조회수 1 증가 - updateReadcount()
	
	
	// 특정 글 1개의 정보 조회 - getBoard(bno)
	
	public BoardDTO getBoard(int bno){
		System.out.println(" DAO : updateReadcount(DAO) 호출");
		BoardDTO dto = null;
		
		try {
			//1.2.  디비연결
			con = getConnect();
			
			//3. sql 작성(select) & pstmt 객체
			sql="select * from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			
			//?
			pstmt.setInt(1, bno);
			
			//4. sql 실행
			rs = pstmt.executeQuery();
			
			//5. 데이터처리
			if(rs.next()){
				//DB에 특정 번호의 글번호를 저장
				
				//DB -> DTO
			      dto = new BoardDTO();
	               dto.setBno(rs.getInt("bno"));
	               dto.setContent(rs.getString("content"));
	               dto.setDate(rs.getDate("date"));
	               dto.setFile(rs.getString("file"));
	               dto.setIp(rs.getString("ip"));
	               dto.setName(rs.getString("name"));
	               dto.setPass(rs.getString("pass"));
	               dto.setRe_lev(rs.getInt("re_lev"));
	               dto.setRe_ref(rs.getInt("re_ref"));
	               dto.setRe_seq(rs.getInt("re_seq"));
	               dto.setReadcount(rs.getInt("readcount"));
	               dto.setSubject(rs.getString("subject"));
			}//if 끝
			
			System.out.println(" DAO : 게시글"+bno+"번 게시글 정보 저장 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
		return dto;
	
	}
	
	
	// 특정 글 1개의 정보 조회 - getBoard(bno)
	
	
	
	
	
	
	
	
	
	
	
	

}// class
