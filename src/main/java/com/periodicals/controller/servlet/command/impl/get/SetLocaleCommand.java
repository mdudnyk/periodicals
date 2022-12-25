package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.User;
import com.periodicals.service.LocaleService;
import com.periodicals.service.impl.LocaleServiceImpl;
import com.periodicals.service.impl.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

public class SetLocaleCommand implements FrontCommand {
    private static final Logger LOG = LogManager.getLogger(SetLocaleCommand.class);

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        DAOManager daoFactory) throws DAOException, IOException {

        String localeNameToSet = request.getParameter("lang");
        LocaleService localeService = new LocaleServiceImpl(daoFactory);
        Map<String, LocaleCustom> localesMap = localeService.getAllLocalesMap();
        LocaleCustom localeObjToSet = localesMap.get(localeNameToSet);

        if (localeObjToSet == null) {
            LOG.info("User with session ID:" + request.getSession().getId() +
                    ", is trying to set to not existing locale: " + localeNameToSet);
            String defaultLocaleName = request.getSession()
                    .getServletContext()
                    .getInitParameter("defaultLocale");
            try {
                localeObjToSet = localeService.getLocaleByShortName(defaultLocaleName);
                LOG.info("Locale object is set to default locale: " + defaultLocaleName);
            } catch (DAOException e) {
                throw new IllegalStateException("Can not find defaultLocale=" + defaultLocaleName +
                        " parameter from web.xml in database. " + e.getMessage());
            }
        }

        request.getSession().setAttribute("locale", localeObjToSet);
        response.addCookie(new Cookie("lang", localeObjToSet.getShortNameId()));

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            user.setLocaleId(localeObjToSet.getShortNameId());
            new UserServiceImpl(daoFactory).updateUserInformation(user);
            LOG.info("User with ID=" + user.getId() + " set his locale to '" + user.getLocaleId() + "'");
        }

        response.sendRedirect(request.getHeader("referer"));
    }
}

