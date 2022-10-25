package com.periodicals.controller.servlet.command;

import com.periodicals.dao.exception.DAOException;

import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.service.exceptions.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface FrontCommand {
    void execute(HttpServletRequest request, HttpServletResponse response,
            DAOManagerFactory daoManager ) throws DAOException, ServletException, IOException, ServiceException;
}
