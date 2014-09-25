package com.getjavajobs.library.webui;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String name = req.getParameter("name");
		if ("Vasya".equals(name)) {
			try {
				resp.getWriter().write("Please change name value");
			} finally {
				resp.flushBuffer();
			}
			return;
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
