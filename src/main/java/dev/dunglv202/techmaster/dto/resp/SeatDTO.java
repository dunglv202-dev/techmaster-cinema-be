package dev.dunglv202.techmaster.dto.resp;

import dev.dunglv202.techmaster.model.Seat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDTO {
    private String code;
    private double price;
    private Seat.Type type;
    private Seat.Status status;
}
