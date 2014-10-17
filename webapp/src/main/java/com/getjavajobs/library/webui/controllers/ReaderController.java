package com.getjavajobs.library.webui.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.ReaderService;

@Controller
public class ReaderController {

	@Autowired
	private ReaderService readerService;

	@RequestMapping("/readers")
	public String view(HttpServletRequest request) {
		try {
			List<Reader> readers = readerService.getAll();
			request.setAttribute("readersList", readers);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/WEB-INF/jsp/readers.jsp";
	}

}
