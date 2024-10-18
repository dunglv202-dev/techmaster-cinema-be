package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.req.BookingRequest;
import dev.dunglv202.techmaster.dto.resp.BookingDTO;
import dev.dunglv202.techmaster.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = MovieMapper.class)
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    Booking toBooking(BookingRequest booking);

    @Mapping(target = "cinema", source = "schedule.cinema.name")
    @Mapping(target = "room", source = "schedule.room.name")
    @Mapping(target = "movie", source = "schedule.movie")
    @Mapping(target = "start", source = "schedule.start")
    BookingDTO toBookingDTO(Booking booking);
}
