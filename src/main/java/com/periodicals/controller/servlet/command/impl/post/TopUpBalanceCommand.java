package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.Payment;
import com.periodicals.entity.User;
import com.periodicals.service.PaymentService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.PaymentServiceImpl;
import com.periodicals.util.LiqPayService;
import com.periodicals.util.MoneyFormatter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class TopUpBalanceCommand implements FrontCommand {

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager) throws DAOException, ServletException,
            IOException, ServiceException {
        User user = (User) request.getSession().getAttribute("user");
        String refererLink = (String) request.getSession().getAttribute("refererForLiqPay");
        String amountOfTopUp = request.getParameter("amount");
        String publicIP = request.getSession().getServletContext().getInitParameter("publicIP");
        String orderId = String.valueOf(user.getId()) + System.currentTimeMillis();

        PaymentService service = new PaymentServiceImpl(daoManager);
        try {
            service.createPayment(new Payment(
                    orderId,
                    user.getId(),
                    MoneyFormatter.toIntegerFormat(amountOfTopUp)));
        } catch (ServiceException e) {
            if (e.getMessage().equals("Account is blocked.")) {
                request.getRequestDispatcher("WEB-INF/error/ErrorBlockedAccount.jsp").forward(request, response);
                return;
            }
        }


        Map<String, String> params = new HashMap<>();
        fillParamsMap(orderId, user, refererLink, amountOfTopUp, params, publicIP);

        String data = LiqPayService.createEncodedData(JSONObject.toJSONString(params));
        String signature = LiqPayService.createSignature(data);

        response.sendRedirect("https://www.liqpay.ua/api/3/checkout?data=" + data + "&signature=" + signature);
    }

    private void fillParamsMap(final String orderId, final User user, final String refererLink,
                               final String amountOfTopUp, final Map<String, String> params, final String publicIP) {
        params.put("action", "pay");
        params.put("public_key", "sandbox_i24456290680");
        params.put("amount", amountOfTopUp != null
                ? amountOfTopUp
                : "0");
        params.put("currency", "UAH");
        params.put("description", "Top up account balance");
        params.put("order_id", "" + orderId);
        params.put("language", user.getLocaleId());
        params.put("version", "3");
        params.put("result_url", refererLink != null
                ? refererLink
                : "http://" + publicIP + "/periodicals/controller?cmd=HOME_PAGE");
        params.put("server_url", "http://" + publicIP + "/periodicals/controller?cmd=GET_PAYMENT_RESULT");
    }
}