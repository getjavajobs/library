package com.getjavajobs.library.webui;

import com.getjavajobs.library.dao.PublisherDao;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.PublisherService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PublisherServlet extends HttpServlet {

    public PublisherService publisherService = null;

    @Override
    public void init() {
        this.publisherService = new PublisherService(new PublisherDao());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String commandType = request.getParameter("commandType");
            switch (commandType) {
                case "add":
                case "update": {

                    String publisherName = request.getParameter("publisherName");
                    String publisherCity = request.getParameter("publisherCity");
                    String publisherPhoneNumber = request.getParameter("publisherPhoneNumber");
                    String publisherEmail = request.getParameter("publisherEmail");
                    String publisherSiteAddress = request.getParameter("publisherSiteAddress");

                    Publisher publisher = new Publisher();
                    publisher.setName(publisherName);
                    publisher.setCity(publisherCity);
                    publisher.setPhoneNumber(publisherPhoneNumber);
                    publisher.setEmail(publisherEmail);
                    publisher.setSiteAddress(publisherSiteAddress);

                    if (commandType.equals("add")) {
                        publisherService.add(publisher);
                    } else {
                        String publisherIdString = request.getParameter("publisherId");
                        int publisherId = Integer.valueOf(publisherIdString);
                        publisher.setId(publisherId);
                        publisherService.update(publisher);
                    }
                    response.sendRedirect("publishers");
                }
                break;

                default: {
                    String publisherIdString = request.getParameter("publisherId");
                    int publisherId = Integer.valueOf(publisherIdString);
                    publisherService.delete(publisherId);
                    response.sendRedirect("publishers");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("error");
        }
    }

}
