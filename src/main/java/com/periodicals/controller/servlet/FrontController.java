package com.periodicals.controller.servlet;

import com.periodicals.controller.servlet.command.CommandFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "FrontController", value = "/controller")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommandFactory.getCommand(request).execute(request, response);
        } catch (Exception e) {
            //TODO logging and error management
            e.printStackTrace();
        }
    }
}