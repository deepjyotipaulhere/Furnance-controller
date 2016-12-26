package com.kotlarz.ddns.dao;

import org.springframework.data.repository.CrudRepository;
import com.kotlarz.ddns.domain.DdnsEntry;

public interface DdnsDao extends CrudRepository<DdnsEntry, Long> {
	public DdnsEntry findByDdnsHost(String ddnsHost);
}
