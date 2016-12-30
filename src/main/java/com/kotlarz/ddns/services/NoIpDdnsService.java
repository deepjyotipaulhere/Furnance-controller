package com.kotlarz.ddns.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kotlarz.ddns.dao.DdnsDao;
import com.kotlarz.ddns.domain.DdnsEntry;
import com.kotlarz.ddns.status.DdnsStatus;
import com.kotlarz.logging.CustomLogger;

@Service
public class NoIpDdnsService implements DdnsService {

	@Autowired
	DdnsDao ddnsDao;

	private static final String NOIP = "NOIP";

	public static final int DDNS_REFRESH_RATE_MIN = 1;

	public static CustomLogger log = CustomLogger.getLogger(NoIpDdnsService.class);

	private Properties getUrl(String urlStr, String username, String password) {
		Properties props = new Properties();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String encoded = encode(username + ":" + password);
			conn.setRequestProperty("Authorization", "Basic " + encoded);
			conn.setRequestProperty("User-Agent", "Java NoIP Updated 1.0");

			Integer responseCode = conn.getResponseCode();
			props.setProperty("code", responseCode.toString());
			log.info("HTTP DDNS code " + responseCode);

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}

			String response = builder.toString();
			props.setProperty("response", response);
			log.info("HTTP DDNS response " + response);
		} catch (Exception ex) {
			props.setProperty("exception", ex.getMessage());
			log.error("HTTP DDNS exception: " + ex.getMessage());
		}

		return props;
	}

	private String encode(String str) {
		Encoder encoder = Base64.getEncoder();
		byte[] encoded = encoder.encode(str.getBytes());
		return new String(encoded);
	}

	@Override
	public DdnsStatus update() {
		DdnsEntry config = ddnsDao.findByDdnsHost(NOIP);

		Properties responseProp = getUrl("http://dynupdate.no-ip.com/nic/update?hostname=" + config.getHostname(),
				config.getUsername(), config.getPassword());

		if (responseProp.containsKey("exception")) {
			if (responseProp.containsKey("code")) {
				Integer code = Integer.parseInt(responseProp.getProperty("code"));

				switch (code) {
				case 401: {
					return DdnsStatus.UNAUTHORIZED;
				}
				default: {
					return DdnsStatus.ERROR;
				}
				}
			} else {
				return DdnsStatus.ERROR;
			}
		} else {
			Integer code = Integer.parseInt(responseProp.getProperty("code"));
			if (responseProp.containsKey("response")) {
				String response = responseProp.getProperty("response");

				switch (code) {
				case 200: {
					if (response.contains("good")) {
						return DdnsStatus.OK;
					} else if (response.contains("nochg")) {
						return DdnsStatus.NO_CHANGE;
					} else if (response.contains("nohost")) {
						return DdnsStatus.NO_HOST;
					} else if (response.contains("badauth")) {
						return DdnsStatus.UNAUTHORIZED;
					} else {
						return DdnsStatus.UNKNOWN;
					}
				}
				default: {
					return DdnsStatus.UNKNOWN;
				}
				}
			} else {
				return DdnsStatus.NO_RESPONSE;
			}
		}
	}

	@Scheduled(fixedRate = 1000 * 60 * DDNS_REFRESH_RATE_MIN)
	private void refresh() {
		DdnsStatus status = update();
		log.info("DDNS update status: " + status.toString());
	}
}