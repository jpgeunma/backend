package com.jpmarket.service;


import com.jpmarket.domain.zone.Zone;
import com.jpmarket.domain.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @PostConstruct
    public void initZoneData() throws IOException {
        if (zoneRepository.count() == 0) {
            Resource resource = new ClassPathResource("zones_kr.csv");
            System.out.println("resources: " + resource);
            List<Zone> zones = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .map(line->{
                        String[] split = line.split(",");
                                return Zone.builder()
                                        .city(split[0])
                                        .localNameOfCity(split[1])
                                        .province(split[2])
                                        .build();
                    }).collect(Collectors.toList());
            zoneRepository.saveAll(zones);
        }
    }
}
