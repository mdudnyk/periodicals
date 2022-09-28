package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.BlockingStatus;
import com.periodicals.entity.enums.Role;
import com.periodicals.service.ServiceException;
import com.periodicals.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SignUpCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User(1, "ua", firstname, lastname, password, email, Role.CUSTOMER, 0, BlockingStatus.NOT_BLOCKED);

        UserService service = new UserService();

        try {
            service.signUpUser(user);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            response.setStatus(461);
            response.setContentType("text/plain");
            try {
                String msg = e.getMessage();
                if (request.getSession().getAttribute("locale").equals("ua")) {
                    msg = "Вибачте, корситувач з таким імейлом вже існує";
                }
                PrintWriter out = response.getWriter();
                out.print(msg);
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}