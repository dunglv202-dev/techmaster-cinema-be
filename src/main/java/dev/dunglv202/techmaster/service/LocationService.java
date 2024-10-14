package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.dto.resp.LocationDTO;

import java.util.List;

public interface LocationService {
    List<LocationDTO> getAll();
}
