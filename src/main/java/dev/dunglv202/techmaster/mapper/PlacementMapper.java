package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.req.PlacementDTO;
import dev.dunglv202.techmaster.entity.Placement;
import org.mapstruct.factory.Mappers;

public interface PlacementMapper {
    PlacementMapper INSTANCE = Mappers.getMapper(PlacementMapper.class);

    Placement toPlacement(PlacementDTO placementDTO);
}
