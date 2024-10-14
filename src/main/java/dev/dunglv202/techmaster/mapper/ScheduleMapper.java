package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.resp.CinemaSchedule;
import dev.dunglv202.techmaster.dto.resp.ScheduleDTO;
import dev.dunglv202.techmaster.entity.Cinema;
import dev.dunglv202.techmaster.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    ScheduleDTO toScheduleDTO(Schedule schedule);

    @Mapping(target = "name", source = "entry.key.name")
    @Mapping(target = "schedules", source = "entry.value")
    CinemaSchedule toCinemaSchedule(Map.Entry<Cinema, List<Schedule>> entry);
}
