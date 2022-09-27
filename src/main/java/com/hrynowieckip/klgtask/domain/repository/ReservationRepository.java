package com.hrynowieckip.klgtask.domain.repository;

import com.hrynowieckip.klgtask.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Set<Reservation> getReservationsByTenantName(String name);

    Set<Reservation> getReservationsByToRentName(String name);

    @Query("SELECT r FROM Reservation r JOIN FETCH r.toRent " +
            "WHERE r.toRent.id = :toRentId AND " +
            ":startDate BETWEEN r.startRentDate AND r.endRentDate OR " +
            ":endDate BETWEEN r.startRentDate AND r.endRentDate OR " +
            "r.startRentDate BETWEEN :startDate AND :endDate")
    Set<Reservation> checkDate(
            @Param("toRentId") Long toRentId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
