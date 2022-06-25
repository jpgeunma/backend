package com.jpmarket.domain.zone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Zone findAllByCityAndProvince(String cityName, String proviceName);
}
