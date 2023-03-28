package com.jerajinsolution.file.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jerajinsolution.file.service.FileDto;
import com.jerajinsolution.file.service.FileInterface;
import com.jerajinsolution.member.service.MemberDto;

@Controller
public class FileController {

	@Autowired
	private FileInterface fileDao;
	
	/* 게시물 파일 다운로드  */
	@RequestMapping(value="/Download.do", method=RequestMethod.GET)
	public String downloadFile(Model model,
			@RequestParam(value="fno", required=true) Long fno,
			HttpSession session,
			HttpServletResponse response) {
		MemberDto userInfo = (MemberDto) session.getAttribute("userInfo");
		
		if(userInfo==null) { //세션에 정보가 없을 경우(로그인하지 않았거나, 이미 로그아웃한 경우)
			model.addAttribute("msg", "먼저 로그인하셔야 합니다.");
			model.addAttribute("url", "Login.do");
			return "/board/result";
		}
		
		FileDto fileDto = fileDao.selectFile(fno);
		
		String fileLocation = "C:/oraclejava/upload";
		
		response.setContentType("application/octet-stream");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=\"" + //다른 이름으로 저장
					URLEncoder.encode(fileDto.getOriginalName(), "UTF-8") + "\""); 
			
			byte[] data = new byte[1024 * 100];
			InputStream is = new BufferedInputStream(new FileInputStream(fileLocation + "/" + fileDto.getFolder() + "/" + fileDto.getTargetName()));
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			while(is.read(data) != -1) {
				os.write(data);
			}
			if(os!=null) try {
				os.flush();
				os.close();}catch(Exception e) {}
			if(is!=null) try {is.close();} catch(Exception e) {}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "file:";
	}
	
	
}
