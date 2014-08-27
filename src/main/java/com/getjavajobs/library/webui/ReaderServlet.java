package com.getjavajobs.library.webui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.ReaderService;

public class ReaderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ReaderService readerService = new ReaderService();

	@Override
	public void init(ServletConfig config) throws ServletException {
		config.getInitParameter("pathToFile");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		try {
			List<Reader> readers = readerService.getAll();
			pw.write("<html><head><title>Readers</title></head><body>READERS<br/><table>");
			for (Reader reader : readers) {
				pw.write("<tr><td>" + reader.getFirstName() + "</td><td>" + reader.getSecondName() + "</td></tr>");
			}
			pw.write("</table></body></html>");
		} catch (ServiceException e) {
			e.printStackTrace(pw);
		} finally {
			resp.flushBuffer();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
