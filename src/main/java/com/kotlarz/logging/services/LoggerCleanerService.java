package com.kotlarz.logging.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kotlarz.logging.CustomLogger;
import com.kotlarz.logging.domain.CustomLogEntry;

@Service
public class LoggerCleanerService {
	@Autowired
	SessionFactory sessionFactory;

	private static CustomLogger log = CustomLogger.getLogger(LoggerCleanerService.class);

	public static final int LOG_CLEANER_RATE_HOURS = 1;
	public static long MAXIMUM_LOGS_COUNT = 10000;

	@Scheduled(fixedRate = 1000 * 3600 * LOG_CLEANER_RATE_HOURS)
	private void logCleaner() {
		log.info("Starting log cleaning service");

		Session session = sessionFactory.openSession();
		log.info("Session opened");
		
		Transaction transaction = session.beginTransaction();
		log.info("Transaction started " + transaction.getStatus().toString());

		try {
			Criteria criteria = session.createCriteria(CustomLogEntry.class);
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Long toDelete = count - MAXIMUM_LOGS_COUNT;

			log.info("To delete " + toDelete.toString());
			if (toDelete <= 0) {
				log.warn("Transaction rollback " + transaction.getStatus().toString());
				transaction.rollback();
				return;
			}

			criteria = session.createCriteria(CustomLogEntry.class);
			criteria.addOrder(Order.asc("date"));
			criteria.setMaxResults(toDelete.intValue());

			@SuppressWarnings("unchecked")
			List<CustomLogEntry> logsToDelete = (List<CustomLogEntry>) criteria.list();
			log.info("Logs to delete: " + logsToDelete.size());

			for (CustomLogEntry log : logsToDelete) {
				session.delete(log);
			}

			transaction.commit();
			log.info("Transaciton commited " + transaction.getStatus().toString());
		} catch (Exception ex) {
			log.error("Exception: " + ex.getMessage());
			transaction.rollback();
			log.warn("Transaction rollback " + transaction.getStatus().toString());
		} finally {
			session.close();
			log.info("Closing session");
		}
	}
}
