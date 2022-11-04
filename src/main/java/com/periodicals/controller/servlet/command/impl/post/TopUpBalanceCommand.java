package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.User;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.util.LiqPayService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;

import java.io.IOException;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopUpBalanceCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager) throws DAOException, ServletException,
                                                                    IOException, ServiceException {
        User user = (User) request.getSession().getAttribute("user");
        String refererLink = (String) request.getSession().getAttribute("refererForLiqPay");
        String amountOfTopUp = request.getParameter("amount");

        Map<String, String> params = new HashMap<>();
        fillParamsMap(user, refererLink, amountOfTopUp, params);

        String data = LiqPayService.createEncodedData(JSONObject.toJSONString(params));
        String signature = LiqPayService.createSignature(data);

        response.sendRedirect("https://www.liqpay.ua/api/3/checkout?data=" + data + "&signature=" + signature);
    }

    private void fillParamsMap(final User user, final String refererLink,
                               final String amountOfTopUp, final Map<String, String> params) {
        params.put("action", "pay");
        params.put("public_key", "sandbox_i24456290680");
        params.put("amount", amountOfTopUp != null
                ? amountOfTopUp
                : "0");
        params.put("currency", "UAH");
        params.put("description", "Top up account balance");
        params.put("order_id", "" + user.getId() + "_" + System.currentTimeMillis());
        params.put("language", user.getLocaleId());
        params.put("version", "3");
        params.put("result_url", refererLink != null
                ? refererLink
                : "http://45.158.51.89/periodicals/controller?cmd=HOME_PAGE");
        params.put("server_url", "http://45.158.51.89/periodicals/controller?cmd=GET_PAYMENT_RESULT");
    }
}