<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Cookie cookie = new Cookie("id", URLEncoder.encode("흥민", "UTF-8")); //한글 인코딩
	cookie.setMaxAge(0);	//쿠키 삭제
	response.addCookie(cookie);
	response.sendRedirect("using_cookie.jsp");
%>