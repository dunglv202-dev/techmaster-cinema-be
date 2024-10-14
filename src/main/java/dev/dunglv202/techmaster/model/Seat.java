package dev.dunglv202.techmaster.model;

import dev.dunglv202.techmaster.util.SeatConverter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat {
    private Type type;
    private Status status;

    public boolean isUnavailable() {
        return status == Status.OCCUPIED || status == Status.NOT_AVAILABLE;
    }

    public enum Type {
        NORMAL,
        VIP
    }

    public enum Status {
        VACANT,
        OCCUPIED,
        NOT_AVAILABLE
    }
}
