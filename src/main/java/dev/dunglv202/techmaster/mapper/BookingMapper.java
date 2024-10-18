package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.dto.req.BookingDTO;
import dev.dunglv202.techmaster.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    Booking toBooking(BookingDTO booking);
}
