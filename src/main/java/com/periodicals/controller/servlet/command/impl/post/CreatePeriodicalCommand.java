package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;



public class CreatePeriodicalCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response, final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
//        String path = request.getServletContext().getRealPath("/");
//        System.out.println(path);

        JSONObject json = getJSONFromRequest(request);
        String imageURL = getImageFromRequest(request);
        System.out.println(json);

        Part imagePart = request.getPart("image");
        FileUtils.writeByteArrayToFile(new File("C:\\Users\\mdudnyk\\Desktop\\image.jpeg"), imagePart.getInputStream().readAllBytes());

    }

    private String getImageFromRequest(final HttpServletRequest request) throws ServletException, IOException {
        String imageUrl = null;
        String defaultImageStorage = request.getSession()
                .getServletContext()
                .getInitParameter("imageStorage");
        String defaultImageURL = request.getSession()
                .getServletContext()
                .getInitParameter("noImageFile");
        Part imagePart = request.getPart("image");

        if (imagePart != null) {

        }
        return null;
    }

    private JSONObject getJSONFromRequest(final HttpServletRequest request) throws ServletException, IOException {
        Part jsonPart = request.getPart("json");
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser
                    .parse(new InputStreamReader(jsonPart.getInputStream()));
        } catch (ParseException e) {
            System.out.println("JSON parser: nothing to parse. " + e.getMessage());
        }
        assert json != null : "No JSON in quest from server. ";
        return json;
    }
}
