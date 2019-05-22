package com.iu.point;

import javax.servlet.http.HttpServletRequest;

import com.iu.action.ActionForward;

public class PointService {
	private PointDAO pointDAO;
	
	public PointService() {
		// TODO Auto-generated constructor stub
		pointDAO = new PointDAO();
	}
	
	public ActionForward selectList(HttpServletRequest request, HttpServletRequest response) {
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
	}
	
	public ActionForward selectOne(HttpServletRequest request, HttpServletRequest response) {
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
	}
}