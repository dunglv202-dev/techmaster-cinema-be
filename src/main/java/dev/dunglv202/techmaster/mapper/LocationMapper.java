package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.resp.LocationDTO;
import dev.dunglv202.techmaster.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO toLocationDTO(Location location);
}
