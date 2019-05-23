package com.iu.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionForward;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchPager;
import com.iu.page.SearchRow;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadDTO;
import com.iu.utill.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class NoticeService implements Action {

	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;

	public NoticeService() {
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();

		int curPage = 1;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1. row
		SearchRow searchRow = s.makeRow();
		ArrayList<NoticeDTO> ar = new ArrayList<NoticeDTO>();
		try {
			ar = noticeDAO.selectList(searchRow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//2. page
			int totalCount = noticeDAO.getTotalCount(searchRow);
			SearchPager searchPager = s.makePage(totalCount);
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/notice/noticeList.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/common/result.jsp");
		}
		
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		//글이 있으면 출력
		//글이 없으면 삭제되었거나 없는 글입니다.(alert) 리스트로
		NoticeDTO noticeDTO = null;
		UploadDTO uploadDTO = null;
		try {
			int num = Integer.parseInt(request.getParameter("num"));
			noticeDTO = noticeDAO.selectOne(num);
			uploadDTO = uploadDAO.selectOne(num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path="";
		if(noticeDTO != null) {
			request.setAttribute("dto", noticeDTO);
			request.setAttribute("upload", uploadDTO);
			path = "../WEB-INF/views/notice/noticeSelect.jsp";
		}else {
			request.setAttribute("message", "No Data");
			request.setAttribute("path", "./noticeList");
			path = "../WEB-INF/views/common/result.jsp";
		}
		actionForward.setCheck(true);
		actionForward.setPath(path);

		return actionForward;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		String method = request.getMethod();//GET, POST
		boolean check=true;
		String path="../WEB-INF/views/notice/noticeWrite.jsp";
		
		if(method.equals("POST")) {
			NoticeDTO noticeDTO = new NoticeDTO();
			//1. request를 하나로 합치기
			//파일을 저장할 디스크 경로(C:
			String saveDirectory=request.getServletContext().getRealPath("upload");
			System.out.println(saveDirectory);
			int maxPostSize=1024*1024*10;//byte
			String encoding = "UTF-8";
			MultipartRequest multi=null;
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//파일저장이 됨.
			//HDD에 저장된 이름
			String fileName = multi.getFilesystemName("f1");//파일의 파라미터 이름
			//클라이언트가 업로드 할 때의 파일명
			String oName = multi.getOriginalFileName("f1");//파일의 파라미터 이름
			//System.out.println("fileName : " + fileName);
			//System.out.println("oName : " + oName);
			UploadDTO uploadDTO = new UploadDTO();
			uploadDTO.setFname(fileName);
			uploadDTO.setOname(oName);
			
			noticeDTO.setTitle(multi.getParameter("title"));
			noticeDTO.setWriter(multi.getParameter("writer"));
			noticeDTO.setContents(multi.getParameter("contents"));
			
			int result=0;
			Connection con = null;
			try {
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				int num = noticeDAO.getNum();
				noticeDTO.setNum(num);
				result = noticeDAO.insert(noticeDTO, con);
				uploadDTO.setNum(num);
				result = uploadDAO.insert(uploadDTO, con);
				if(result < 1) {
					throw new Exception();
				}
				con.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				result = 0;
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(result>0) {
				check=false;
				path="./noticeList";
				
			}else {
				request.setAttribute("message", "Write Fail");
				request.setAttribute("path", "./noticeList");
				check=true;
				path="../WEB-INF/views/common/result.jsp";
				
			}//post 끝
			
		}
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();

		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/notice/noticeUpdate.jsp");

		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}
}