package com.kotlarz.logging;

import java.util.Date;
import org.apache.log4j.Logger;
import com.kotlarz.logging.dao.CustomLogDao;
import com.kotlarz.logging.domain.CustomLogEntry;

public class CustomLogger {

	public static CustomLogDao logDao;

	public static final long MAXIMUM_LOGS_COUNT = 10000;
	public static final long LOG_CLEANER_RATE_HOURS = 1;

	private Logger logger;

	public static CustomLogger getLogger(Class<?> clazz) {
		return new CustomLogger(Logger.getLogger(clazz));
	}

	protected CustomLogger(Logger logger) {
		this.logger = logger;
		// logDao = context.getBean(CustomLogDao.class);
		// sessionFactory = context.getBean(SessionFactory.class);
	}

	public CustomLogEntry createLog(String message, String logType) {
		CustomLogEntry log = new CustomLogEntry();
		log.setDate(new Date());
		log.setLoggerName(logger.getName());
		log.setLogType(logType);
		log.setMessage(message);
		return log;
	}

	public void info(Object message) {
		logger.info(message);
		CustomLogEntry log = createLog(message.toString(), "INFO");
		logDao.save(log);
	}

	public void debug(Object message) {
		logger.debug(message);
		CustomLogEntry log = createLog(message.toString(), "DEBUG");
		logDao.save(log);
	}

	public void error(Object message) {
		logger.error(message);
		CustomLogEntry log = createLog(message.toString(), "ERROR");
		logDao.save(log);
	}

	public void fatal(Object message) {
		logger.fatal(message);
		CustomLogEntry log = createLog(message.toString(), "FATAL");
		logDao.save(log);
	}

	public void warn(Object message) {
		logger.warn(message);
		CustomLogEntry log = createLog(message.toString(), "WARN");
		logDao.save(log);
	}

	public void trace(Object message) {
		logger.trace(message);
		CustomLogEntry log = createLog(message.toString(), "TRACE");
		logDao.save(log);
	}
}
