package com.kotlarz.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kotlarz.database.exceptions.UnsupportedDatabaseException;
import com.kotlarz.logging.CustomLogger;
import com.kotlarz.logging.dao.CustomLogDao;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	private static String url = "jdbc:mysql";
	private static String host = "localhost";
	private static String databaseName = "raspberry_furnance";
	private static String username = "root";
	private static String password = "root";
	private static String urlParameters = "autoReconnect=true&useSSL=false&characterEncoding=utf-8";
	private static Integer port = 5432;
	private static String driverClassName = "com.mysql.jdbc.Driver";

	public static void initProperties(String propertiesFilePath) throws IOException, UnsupportedDatabaseException {
		FileInputStream stream = new FileInputStream(propertiesFilePath);
		Properties properties = new Properties();
		properties.load(stream);

		DatabaseType type = DatabaseType.valueOf(properties.getProperty("databaseType"));
		host = properties.getProperty("host");
		databaseName = properties.getProperty("databaseName");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		port = Integer.parseInt(properties.getProperty("port"));

		switch (type) {
		case MYSQL: {
			url = "jdbc:mysql";
			driverClassName = "com.mysql.jdbc.Driver";
			break;
		}
		case POSTGRESQL: {
			url = "jdbc:postgresql";
			driverClassName = "org.postgresql.Driver";
			break;
		}
		case SQLITE: {
			url = "jdbc:mysql";
			driverClassName = "com.mysql.jdbc.Driver";
			break;
		}
		default: {
			throw new UnsupportedDatabaseException(
					"Database " + properties.getProperty("database") + " is not supported");
		}
		}

		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException ex) {
			throw new UnsupportedDatabaseException("Not found driver is system, class " + driverClassName, ex);
		}
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		StringBuilder builder = new StringBuilder();
		builder.append(url).append("://").append(host).append(":").append(port.toString()).append("/")
				.append(databaseName).append("?").append(urlParameters);

		dataSource.setUrl(builder.toString());
		return dataSource;

	}

	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.kotlarz");
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public SessionFactory sessionFactory(EntityManagerFactory factory) {
		return factory.unwrap(SessionFactory.class);
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	@Autowired
	CustomLogDao customLogDao;

	@PostConstruct
	public void inStartup() {
		CustomLogger.logDao = customLogDao;
	}
}
