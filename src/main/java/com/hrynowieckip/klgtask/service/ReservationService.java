package com.hrynowieckip.klgtask.service;

import com.hrynowieckip.klgtask.domain.form.ChangeReservationForm;
import com.hrynowieckip.klgtask.domain.form.NewReservationForm;
import com.hrynowieckip.klgtask.domain.model.Reservation;

import java.util.Date;
import java.util.Set;

public interface ReservationService {
    Set<Reservation> getReservationsForTenantName(String name);

    Set<Reservation> getReservationsForToRentName(String name);

    Long newReservation(NewReservationForm newReservationForm);

    Long changeReservation(ChangeReservationForm changeReservationForm);

    boolean isReservationDateValid(Long toRentId, Date startDate, Date endDate);

    Reservation getReservationById(Long id);
}
