package com.hrynowieckip.klgtask.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "tenant")
    @ToString.Exclude
    private Set<Reservation> reservationsAsTenant;
    @JsonIgnore
    @OneToMany(mappedBy = "landlord")
    @ToString.Exclude
    private Set<Reservation> reservationsAsLandlord;

    public void addToReservationsAsTenant(Reservation reservation) {
        if (reservationsAsTenant.isEmpty()) {
            reservationsAsTenant = new HashSet<>();
        }
        reservationsAsTenant.add(reservation);
    }

    public void addToReservationsAsLandlord(Reservation reservation) {
        if (reservationsAsLandlord.isEmpty()) {
            reservationsAsLandlord = new HashSet<>();
        }
        reservationsAsLandlord.add(reservation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
