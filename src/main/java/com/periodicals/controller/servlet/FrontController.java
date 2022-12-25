package com.periodicals.controller.servlet;

import com.periodicals.controller.servlet.command.CommandFactory;
import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.util.CommandAccessChecker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "FrontController", value = "/controller")
@MultipartConfig(fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            DAOManager daoFactory =
                    (DAOManager) request
                    .getServletContext()
                    .getAttribute("DAOManagerFactory");
            FrontCommand command = CommandFactory.getCommand(request);
            if (CommandAccessChecker.isAccessAllowed(command, request)) {
                command.execute(request, response, daoFactory);
            } else {
                response.setStatus(567);
                request.getRequestDispatcher("WEB-INF/error/ErrorAuthorization.jsp").forward(request, response);
            }
        } catch (Exception e) {
            //TODO logging
            throw new ServletException(e);
        }
    }
}