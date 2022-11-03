package com.periodicals.controller.servlet.command.impl.post;

import com.liqpay.LiqPay;
import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.service.exceptions.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


public class TopUpBalanceCommand implements FrontCommand {



    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager) throws DAOException, ServletException,
                                                                    IOException, ServiceException {
        String PUBLIC_KEY = "sandbox_i24456290680";
        String PRIVATE_KEY = "sandbox_6a5vVJL8YvUtlmIINZsAiZsayb33EdOSgy7QcqWK";

        Map<String, String> params = new HashMap<>();
        params.put("action", "pay");
        params.put("amount", "10");
        params.put("currency", "UAH");
        params.put("description", "press reader top up balance");
        params.put("order_id", "order_id_1223");
        params.put("version", "3");
        params.put("server_url", "http://45.158.51.89/periodicals/controller?cmd=GET_RESULTS");

        LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
        liqpay.setCnbSandbox(true);

        String htmlForm = liqpay.cnb_form(params);
        System.out.println(htmlForm);
        response.getWriter().print(htmlForm);
        response.setContentType("text/html");
    }
}
