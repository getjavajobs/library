package com.getjavajobs.library.webui.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/readers/filter", method = RequestMethod.GET)
	@ResponseBody
	public List<Reader> filter(final @RequestParam("query") String query) throws ServiceException {
		System.out.println("Query = " + query);
		List<Reader> readers = readerService.getAll();
		// filter query
		CollectionUtils.filter(readers, new Predicate<Reader>() {
			@Override
			public boolean evaluate(Reader reader) {
				return reader.getFirstName().startsWith(query);
			}
		});
		return readers;
	}

}
