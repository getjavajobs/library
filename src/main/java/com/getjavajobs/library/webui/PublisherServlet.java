package com.getjavajobs.library.webui;

import com.getjavajobs.library.dao.PublisherDao;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.PublisherService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PublisherServlet extends HttpServlet {

    public final PublisherService publisherService = new PublisherService(new PublisherDao());

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {

        String commandType = (String) request.getAttribute("commandType");

        switch (commandType) {
            case "add":
            case "update": {
                Publisher publisher = new Publisher();
                publisher.setName(request.getParameter("publisherName"));
                publisher.setCity(request.getParameter("publisherCity"));
                publisher.setPhoneNumber(request.getParameter("publisherPhoneNumber"));
                publisher.setEmail(request.getParameter("publisherEmail"));
                publisher.setSiteAddress(request.getParameter("publisherSiteAddress"));

                if (commandType.equals("update")) {
                    int id = Integer.parseInt(request.getParameter("publisherId"));
                    try {
                        publisherService.update(publisher);
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        publisherService.add(publisher);
                    } catch (ServiceException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            break;

            case "delete": {
                int id = Integer.parseInt(request.getParameter("publisherId"));

                try {
                    publisherService.delete(id);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    public Publisher getById(int id) {
        Publisher publisher = null;
        try {
            publisher = publisherService.get(id);
        } catch (ServiceException ex) {
            ex.printStackTrace();
        }
        return publisher;
    }

    public List<Publisher> getAllPublishers() {
        List<Publisher> publisherList = null;
        try {
            publisherList = publisherService.getAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return publisherList;
    }

}
