package com.periodicals.controller.servlet.listener;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = LogManager.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		LOG.debug("Here is some DEBUG");
		LOG.info("Here is some INFO");
		LOG.warn("Here is some WARN");
		LOG.error("Here is some ERROR");
		LOG.trace("Here is some TRACE");
		LOG.fatal("Here is some FATAL");

		LOG.debug("Start context initialization");
		ServletContext context = sce.getServletContext();
		initDatasource(context);
		initLocaleList(context);
		LOG.debug("End context initialization");
	}

	private void initDatasource(ServletContext context) throws IllegalStateException {
		try {
			DAOManagerFactory dmf = DAOManagerFactory.getInstance();
			context.setAttribute("DAOManagerFactory", dmf);
			LOG.debug("context.setAttribute 'DAOManagerFactory': {}", dmf.getClass().getName());
		} catch (DAOException e) {
			LOG.error("Cannot initialize dataSource. " + e.getCause().getMessage());
			throw new IllegalStateException(e);
		}
		LOG.debug("DataSource initialized");
	}

	private void initLocaleList(ServletContext context) {
		LOG.debug("Locale list initialized");
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
			LOG.debug("Deregister JDBC drivers");
		} catch (SQLException e) {
			LOG.error("Cannot deregister JDBC drivers while shutting down. " + e.getCause().getMessage());
		}
		LOG.debug("End context destroying");
	}
}
