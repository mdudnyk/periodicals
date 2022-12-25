package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.service.PaymentService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.PaymentServiceImpl;
import com.periodicals.util.LiqPayService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GetPaymentResultCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String data = request.getParameter("data");
        String signature = request.getParameter("signature");

        byte[] decodedBytes = Base64.getDecoder().decode(data);
        String decodedDataString = new String(decodedBytes, StandardCharsets.UTF_8);

        if (isValidSignature(signature, decodedDataString)) {
            JSONObject jsonData;
            JSONParser parser = new JSONParser();
            try {
                jsonData = (JSONObject) parser.parse(decodedDataString);
            } catch (ParseException e) {
                e.printStackTrace();
                response.setStatus(404);
                return;
            }
            String orderId = (String) jsonData.get("order_id");
            String status = (String) jsonData.get("status");

            PaymentService service = new PaymentServiceImpl(daoManager);
            try {
                service.processPaymentStatus(orderId, status);
            } catch (ServiceException e) {
                //TODO <logger> e.getMessage();
            }
        } else {
            response.setStatus(404);
            return;
        }

        request.getSession().invalidate();
    }

    private boolean isValidSignature(final String signature, final String decodedDataString) {
        String generatedSignature = LiqPayService.createSignatureFromJSONString(decodedDataString);
        return signature.equals(generatedSignature);
    }
}