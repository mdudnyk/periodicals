package com.periodicals.controller.servlet.filter;

import com.periodicals.entity.LocaleCustom;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "LocaleFilter", urlPatterns = { "/*" })
public class LocaleFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(LocaleFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Map<String, LocaleCustom> appLocales =
                (Map<String, LocaleCustom>) req.getServletContext().getAttribute("locales");

        Cookie langCookie = setLangCookie(req, resp, appLocales);
        setSessionLocaleAttribute(req, appLocales, langCookie);

        chain.doFilter(request, response);
    }

    private void setSessionLocaleAttribute(final HttpServletRequest req,
                                           final Map<String, LocaleCustom> appLocales,
                                           final Cookie langCookie) {
        req.getSession().setAttribute("locale", appLocales.get(langCookie.getValue()));
    }

    private Cookie setLangCookie(final HttpServletRequest req, final HttpServletResponse resp,
                               final Map<String, LocaleCustom> appLocales) {
        Cookie langCookie = searchLangCookie(req.getCookies());
        if (!isValidLangCookie(langCookie, appLocales)) {
            String defaultLocaleName = req.getSession()
                    .getServletContext()
                    .getInitParameter("defaultLocale");
            if (defaultLocaleName != null) {
                langCookie = new Cookie("lang", defaultLocaleName);
                resp.addCookie(langCookie);
                LOG.info("'lang' cookie for session ID=" + req.getSession().getId()
                        + " is set from web.xml`s 'defaultLocale' parameter with value: "
                        + defaultLocaleName);
            } else {
                String error = "Can not get 'defaultLocale' parameter from web.xml";
                LOG.error(error);
                throw new IllegalStateException(error);
            }
        }
        return langCookie;
    }

    boolean isValidLangCookie(Cookie c, Map<String, LocaleCustom> locales) {
        if (c != null) {
            return locales.get(c.getValue()) != null;
        }
        return false;
    }

    private Cookie searchLangCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("lang")) {
                    return c;
                }
            }
        }
        return null;
    }
}
