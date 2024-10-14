package dev.dunglv202.techmaster.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat {
    private Type type;
    private Status status;

    public enum Type {
        REGULAR,
        VIP
    }

    public enum Status {
        VACANT,
        OCCUPIED,
        NOT_AVAILABLE
    }
}
