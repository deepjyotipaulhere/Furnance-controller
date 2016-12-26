package com.kotlarz.ddns.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DdnsEntry {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String password;
	private String hostname;
	private String ddnsHost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDdnsHost() {
		return ddnsHost;
	}

	public void setDdnsHost(String ddnsHost) {
		this.ddnsHost = ddnsHost;
	}

}
