package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalTranslate;
import com.periodicals.service.PeriodicalService;
import com.periodicals.service.ServiceException;
import com.periodicals.service.impl.PeriodicalServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreatePeriodicalCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        JSONObject periodicalJson = getPeriodicalJSONFromRequest(request);
        String imageURL = getGeneratedImageURL(request);
        Periodical newPeriodical = fillEntityFromRequest(periodicalJson, imageURL);

        PeriodicalService ps = new PeriodicalServiceImpl(daoManager);
        try {
            ps.createPeriodical(newPeriodical);
            Part imagePart = request.getPart("image");
            FileUtils.writeByteArrayToFile(new File(imageURL),
                    imagePart.getInputStream().readAllBytes());
        } catch (ServiceException e) {
            response.setStatus(564);
        }
    }

    private Periodical fillEntityFromRequest(final JSONObject json, final String imageURL) {
        Periodical periodical;
        Object o;
        try {
            o = json.get("topic");
            assert o != null;
            long topic = (Long) o;

            o = json.get("title");
            assert o != null;
            String title = (String) o;

            o = json.get("price");
            assert o != null;
            long price = (Long) o;

            o = json.get("frequency");
            assert o != null;
            JSONObject frequency = (JSONObject) o;

            o = json.get("subscription_period");
            assert o != null;
            long subPeriod = (Long) o;

            o = json.get("status_value");
            assert o != null;
            boolean isActive = (boolean) o;

            Map<String, PeriodicalTranslate> translations = getTranslationsFromJSON(json);
            Map<Integer, MonthSelector> releaseCalendar = getReleaseCalendarFromJSON(json);
            periodical =
                    new Periodical((int) topic, title, imageURL,
                            (int) price, frequency, (int) subPeriod, isActive);
            periodical.setTranslation(translations);
            periodical.setReleaseCalendar(releaseCalendar);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format of Periodical object. " + e.getMessage());
        }
        return periodical;
    }

    private Map<Integer, MonthSelector> getReleaseCalendarFromJSON(final JSONObject json) {
        Map<Integer, MonthSelector> releaseCalendar = new HashMap<>();
        Object o;

        try {
            o = json.get("release");
            assert o != null;
            JSONArray releaseArr = (JSONArray) o;

            for (Object value : releaseArr) {
                JSONObject tmp = (JSONObject) value;
                long year = (long) tmp.get("year");
                JSONArray month = (JSONArray) tmp.get("month");
                releaseCalendar.put((int) year, new MonthSelector((int) year, month));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format of Periodical object. " + e.getMessage());
        }

        return releaseCalendar;
    }

    private Map<String, PeriodicalTranslate> getTranslationsFromJSON(final JSONObject json) {
        Map<String, PeriodicalTranslate> translations = new HashMap<>();
        Object o;

        try {
            o = json.get("country");
            assert o != null;
            JSONArray countryArr = (JSONArray) o;

            o = json.get("language");
            assert o != null;
            JSONArray languageArr = (JSONArray) o;

            o = json.get("description");
            assert o != null;
            JSONArray descriptionArr = (JSONArray) o;

            for (int i = 0; i < countryArr.size(); i++) {
                JSONObject tmp = (JSONObject) countryArr.get(i);
                String locale = (String) tmp.get("lang");
                String country = (String) tmp.get("value");

                tmp = (JSONObject) languageArr.get(i);
                String language = (String) tmp.get("value");

                tmp = (JSONObject) descriptionArr.get(i);
                String description = (String) tmp.get("value");

                translations.put(locale, new PeriodicalTranslate(locale, country, language, description));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format of Periodical object. " + e.getMessage());
        }
        return translations;
    }

    private String getGeneratedImageURL(final HttpServletRequest request) throws ServletException, IOException {
        String fileName;
        String defaultImageStorage = request.getSession()
                .getServletContext()
                .getInitParameter("imageStorage");
        Part imagePart = request.getPart("image");

        if (imagePart == null) {
            String defaultImageURL = request.getSession()
                    .getServletContext()
                    .getInitParameter("noImageFile");
            assert defaultImageURL != null : "No default title image file url in web.xml. ";
            return defaultImageURL;
        } else {
            fileName = generateRandomImageName();
        }
        return defaultImageStorage + fileName;
    }

    private String generateRandomImageName() {
        Random random = new Random();
        long currentTimestamp = System.currentTimeMillis();
        int randomInt = random.nextInt(99 - 10) + 10;
        return "" + currentTimestamp + randomInt + ".jpeg";
    }

    private JSONObject getPeriodicalJSONFromRequest(final HttpServletRequest request) throws ServletException, IOException {
        Part jsonPart = request.getPart("json");
        String periodicalJSONKey = "periodical";
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        JSONObject periodicalJSON;
        try {
            json = (JSONObject) parser
                    .parse(new InputStreamReader(jsonPart.getInputStream()));
        } catch (ParseException e) {
            System.out.println("JSON parser: nothing to parse. " + e.getMessage());
        }
        assert json != null : "No any JSON in quest from server. ";
        periodicalJSON = (JSONObject) json.get(periodicalJSONKey);
        assert periodicalJSON != null : "No JSON with key=" + periodicalJSONKey + " in quest from server. ";
        return periodicalJSON;
    }
}