package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.UserService;
import com.periodicals.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignUpCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoFactory) throws DAOException {

        LocaleCustom localeObj = (LocaleCustom) request.getSession().getAttribute("locale");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User(1, localeObj.getShortNameId() , firstname, lastname,
                password, email, UserRole.CUSTOMER, 0, false);

        UserService service = new UserServiceImpl(daoFactory);

        try {
            service.signUpUser(user);
        } catch (ServiceException e) {
            response.setStatus(561);
        }
    }
}