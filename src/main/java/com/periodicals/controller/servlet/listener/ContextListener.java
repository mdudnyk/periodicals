package com.periodicals.controller.servlet.listener;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.service.LocaleService;
import com.periodicals.service.impl.LocaleServiceImpl;
import jakarta.servlet.*;

import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = LogManager.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		LOG.debug("Start of context initialization");
		ServletContext context = sce.getServletContext();
		initDatasource(context);
		initLocaleList(context);
		LOG.debug("End of context initialization");
	}

	private void initDatasource(ServletContext context) throws IllegalStateException {
		try {
			DAOManagerFactory dmf = DAOManagerFactory.getInstance();
			LOG.debug("Data source initialized");
			context.setAttribute("DAOManagerFactory", dmf);
			LOG.debug("context.setAttribute 'DAOManagerFactory': {}", dmf.getClass().getName());
		} catch (DAOException e) {
			LOG.error("Cannot initialize dataSource. " + e.getCause().getMessage());
			throw new IllegalStateException(e);
		}
	}

	private void initLocaleList(ServletContext context) {
		Object o = context.getAttribute("DAOManagerFactory");
		if (o != null) {
			DAOManagerFactory dmf = (DAOManagerFactory) o;
			LocaleService localeService = new LocaleServiceImpl(dmf);
			try {
				Map<String, LocaleCustom> locales = localeService.getAllLocalesMap();
				if (locales.size() > 0) {
					context.setAttribute("locales", locales);
				} else {
					LOG.error("Did not set context attribute 'locales', because locale list from database has size < 1");
					throw new DAOException("No locales get from database, can not start application without locales");
				}
				LOG.debug("context.setAttribute 'locales': {}", locales.keySet());
			} catch (DAOException e) {
				LOG.error("Cannot initialize data source. " + e.getCause().getMessage());
				throw new IllegalStateException(e);
			}
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		LOG.debug("Start context destroying");
		Object o = sce.getServletContext().getAttribute("DAOManagerFactory");
		if (o != null) {
			DAOManagerFactory dmf = (DAOManagerFactory) o;
			dmf.closeDAO();
			LOG.debug("Closed DAO manager factory datasource");
		}
		try {
			while (DriverManager.getDrivers().hasMoreElements()) {
				DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
			}
			LOG.debug("Completed to unregister JDBC drivers");
		} catch (SQLException e) {
			LOG.error("Cannot unregister JDBC drivers while shutting down. " + e.getCause().getMessage());
		}
		LOG.debug("End context destroying");
	}

}
