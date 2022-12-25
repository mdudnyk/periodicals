package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.User;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.impl.SubscriptionsServiceImpl;
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
                        final HttpServletResponse response, final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        User user = (User) request.getSession().getAttribute("user");
        String periodicalId = request.getParameter("id");

        try {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(request.getReader());
            List<MonthSelector> subscriptionCalendar = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject jso = (JSONObject) o;
                long year = (Long) jso.get("year");
                JSONArray months = (JSONArray) jso.get("month");
                MonthSelector monthSelector = new MonthSelector((int) year, months);
                subscriptionCalendar.add(monthSelector);
            }
            SubscriptionsService subscriptionsService = new SubscriptionsServiceImpl(daoManager);
            try {
                subscriptionsService.createSubscription(user, Integer.parseInt(periodicalId), subscriptionCalendar);
            } catch (ServiceException e) {
                if (e.getMessage().equals("Account is blocked.")) {
                    response.setStatus(569);
                } else if (e.getMessage().equals("Not enough money.")) {
                    response.setStatus(568);
                }
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid SUBSCRIBE request format. ");
        }
    }
}