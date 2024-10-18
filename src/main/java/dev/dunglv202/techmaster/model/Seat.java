package dev.dunglv202.techmaster.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dunglv202.techmaster.util.SeatConverter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat {
    private Type type;
    private Status status;

    @JsonIgnore
    public boolean isUnavailable() {
        return status == Status.OCCUPIED || status == Status.NOT_AVAILABLE;
    }

    public enum Type {
        STANDARD,
        VIP
    }

    public enum Status {
        VACANT,
        OCCUPIED,
        NOT_AVAILABLE
    }
}
