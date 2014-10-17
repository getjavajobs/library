package com.getjavajobs.library.webui.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.ReaderService;

@Controller
public class ReaderController {

	@Autowired
	private ReaderService readerService;

	@RequestMapping("/readers/${id}/view")
	public String view(
			HttpServletRequest request,
			@RequestParam("name") double name,
			@PathVariable("id") int id) {
		try {
			System.out.println("Name = " + name);
			List<Reader> readers = readerService.getAll();
			request.setAttribute("readersList", readers);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/WEB-INF/jsp/readers.jsp";
	}

}
