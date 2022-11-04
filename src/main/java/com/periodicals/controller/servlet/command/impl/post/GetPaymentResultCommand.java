package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.util.LiqPayService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;


public class GetPaymentResultCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String data = request.getParameter("data");
        String signature = request.getParameter("signature");

        byte[] decodedBytes = Base64.getDecoder().decode(data);
        String decodedDataString = new String(decodedBytes, StandardCharsets.UTF_8);

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                System.out.println("cookie: " + c.getName() + ", value:" + c.getValue());
            }
        }

        if (isValidSignature(signature, decodedDataString)) {
            JSONParser parser = new JSONParser();
            try {
                System.out.println(decodedDataString);
                JSONObject jsonData = (JSONObject) parser.parse(decodedDataString);
                String status = (String) jsonData.get("status");
                Double amount = (Double) jsonData.get("amount");
                System.out.println("status: " + status + "\namount: " + amount);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        System.out.println("REQUEST/////////////////");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        response.setHeader("connection", "close");
        System.out.println("RESPONSE////////////////");
        response.getHeaderNames().forEach(s -> System.out.println(s + ": " + response.getHeader(s)));

        request.getSession().invalidate();

    }

    private boolean isValidSignature(final String signature, final String decodedDataString) {
        String generatedSignature = LiqPayService.createSignatureFromJSONString(decodedDataString);
        return signature.equals(generatedSignature);
    }
}
