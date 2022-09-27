package com.hrynowieckip.klgtask.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class ToRent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;
    private BigDecimal price;
    private Double area;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "toRent")
    @ToString.Exclude
    private Set<Reservation> reservations;

    public void addToReservations(Reservation reservation) {
        if (reservations.isEmpty()) {
            reservations = new HashSet<>();
        }
        reservations.add(reservation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ToRent toRent = (ToRent) o;
        return id != null && Objects.equals(id, toRent.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
