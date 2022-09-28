package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;
import com.periodicals.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignInCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = null;

        UserService service = new UserService();

        try {
            user = service.signInUser(email, password);
        } catch (DAOException e) {
            try{
                response.sendError(461, "Something wrong. Try again later");
            } catch (IOException a) {
                a.printStackTrace();
            }
            e.printStackTrace();
        }

        if (user != null) {
            request.getSession().setAttribute("user", user);
        } else {
            try{
                response.sendError(460, "Can`t find such user");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
