package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.User;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.UserService;
import com.periodicals.service.impl.SubscriptionsServiceImpl;
import com.periodicals.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubscribeCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response, final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        User user = (User) request.getSession().getAttribute("user");
        String periodicalId = request.getParameter("id");

        try {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(request.getReader());
            List<MonthSelector> calendar = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject jso = (JSONObject) o;
                long year = (Long) jso.get("year");
                JSONArray months = (JSONArray) jso.get("month");
                MonthSelector monthSelector = new MonthSelector((int) year, months);
                calendar.add(monthSelector);
            }
            SubscriptionsService subscriptionsService = new SubscriptionsServiceImpl(daoManager);
            UserService userService = new UserServiceImpl(daoManager);
            try {
                subscriptionsService.createSubscription(user.getId(), Integer.parseInt(periodicalId), calendar);
//                request.getSession().setAttribute("user", userService.getUser(user.getId()));
            } catch (ServiceException e) {
                response.setStatus(568);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid SUBSCRIBE request format. ");
        }
    }
}
