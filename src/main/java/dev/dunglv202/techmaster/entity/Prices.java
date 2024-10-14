package dev.dunglv202.techmaster.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Prices {
    private double normalPrice;
    private double vipPrice;
}
