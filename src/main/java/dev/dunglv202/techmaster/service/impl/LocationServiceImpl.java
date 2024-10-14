package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.dto.resp.LocationDTO;
import dev.dunglv202.techmaster.mapper.LocationMapper;
import dev.dunglv202.techmaster.repository.LocationRepository;
import dev.dunglv202.techmaster.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public List<LocationDTO> getAll() {
        return locationRepository.findAll()
            .stream()
            .map(LocationMapper.INSTANCE::toLocationDTO)
            .toList();
    }
}
