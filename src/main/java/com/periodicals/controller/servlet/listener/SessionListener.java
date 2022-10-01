package com.periodicals.controller.servlet.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    private static final Logger LOG = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(final HttpSessionEvent se) {
        LOG.info("Session is created: " + se.getSession().getId());
        se.getSession().setMaxInactiveInterval(1800);

    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent se) {
        LOG.info("Session is destroyed: " + se.getSession().getId());
    }
}
