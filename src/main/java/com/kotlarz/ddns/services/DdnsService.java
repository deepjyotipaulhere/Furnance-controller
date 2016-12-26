package com.kotlarz.ddns.services;

import org.springframework.stereotype.Service;

import com.kotlarz.ddns.status.DdnsStatus;

@Service
public interface DdnsService {
	public DdnsStatus update();
}
