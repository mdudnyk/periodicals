package com.periodicals.controller.servlet;

import com.periodicals.controller.servlet.command.CommandFactory;
import com.periodicals.dao.manager.DAOManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "FrontController", value = "/controller")
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
            DAOManagerFactory daoFactory =
                    (DAOManagerFactory) request
                    .getServletContext()
                    .getAttribute("DAOManagerFactory");
            CommandFactory.getCommand(request).execute(request, response, daoFactory);
        } catch (Exception e) {
            //TODO logging
            throw new ServletException(e);
        }
    }
}