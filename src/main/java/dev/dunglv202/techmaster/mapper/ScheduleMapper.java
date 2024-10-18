package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.resp.CinemaSchedule;
import dev.dunglv202.techmaster.dto.resp.ScheduleDTO;
import dev.dunglv202.techmaster.dto.resp.ScheduleDetailsDTO;
import dev.dunglv202.techmaster.dto.resp.SeatDTO;
import dev.dunglv202.techmaster.entity.Cinema;
import dev.dunglv202.techmaster.entity.Schedule;
import dev.dunglv202.techmaster.model.Seat;
import dev.dunglv202.techmaster.util.SeatConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(uses = MovieMapper.class)
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    ScheduleDTO toScheduleDTO(Schedule schedule);

    @Mapping(target = "name", source = "entry.key.name")
    @Mapping(target = "schedules", source = "entry.value")
    CinemaSchedule toCinemaSchedule(Map.Entry<Cinema, List<Schedule>> entry);

    @Mapping(target = "cinema", source = "cinema.name")
    @Mapping(target = "room", source = "room.name")
    @Mapping(target = "seats", expression = "java(getSeatDTOs(schedule))")
    ScheduleDetailsDTO toScheduleDetailDTO(Schedule schedule);

    SeatDTO toSeatDTO(Seat seat);

    default List<List<SeatDTO>> getSeatDTOs(Schedule schedule) {
        List<List<SeatDTO>> seats = new ArrayList<>();
        for (int row = 0; row < schedule.getSeats().size(); row++) {
            List<SeatDTO> rowSeats = new ArrayList<>();
            for (int col = 0; col < schedule.getSeats().get(row).size(); col++) {
                Seat seat = schedule.getSeats().get(row).get(col);
                if (seat == null) {
                    rowSeats.add(null);
                    continue;
                }
                String seatCode = SeatConverter.format(row, col);
                SeatDTO seatDTO = toSeatDTO(seat);
                seatDTO.setPrice(schedule.getPrice(seat));
                seatDTO.setCode(seatCode);
                rowSeats.add(seatDTO);
            }
            seats.add(rowSeats);
        }
        return seats;
    }
}
