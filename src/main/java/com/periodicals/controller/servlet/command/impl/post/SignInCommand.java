package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import com.periodicals.service.UserService;
import com.periodicals.service.impl.LocaleServiceImpl;
import com.periodicals.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SignInCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoFactory) throws DAOException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserService userService = new UserServiceImpl(daoFactory);
        User user = userService.signInUser(email, password);

        if (user != null) {
            if (user.getRole() == UserRole.CUSTOMER && user.isBlocked()) {
                response.sendError(559, "User is blocked.");
            } else {
                LocaleCustom userLocale = new LocaleServiceImpl(daoFactory)
                        .getLocaleByShortName(user.getLocaleId());
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("locale", userLocale);
                response.addCookie(new Cookie("lang", user.getLocaleId()));
            }
        } else {
            response.sendError(560, "Can`t find such user.");
        }
    }
}
