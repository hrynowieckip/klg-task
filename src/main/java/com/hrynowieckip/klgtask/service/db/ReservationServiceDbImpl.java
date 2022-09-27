package com.hrynowieckip.klgtask.service.db;

import com.hrynowieckip.klgtask.domain.form.ChangeReservationForm;
import com.hrynowieckip.klgtask.domain.form.NewReservationForm;
import com.hrynowieckip.klgtask.domain.model.Client;
import com.hrynowieckip.klgtask.domain.model.Reservation;
import com.hrynowieckip.klgtask.domain.model.ToRent;
import com.hrynowieckip.klgtask.domain.repository.ReservationRepository;
import com.hrynowieckip.klgtask.exception.IncorrectReservationDateException;
import com.hrynowieckip.klgtask.exception.ReservationNotFoundException;
import com.hrynowieckip.klgtask.service.ClientService;
import com.hrynowieckip.klgtask.service.ReservationService;
import com.hrynowieckip.klgtask.service.ToRentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceDbImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final ToRentService toRentService;

    @Override
    public Set<Reservation> getReservationsForTenantName(String name) {
        log.debug("Getting reservations for tenant name: {}", name);
        return reservationRepository.getReservationsByTenantName(name);
    }

    @Override
    public Set<Reservation> getReservationsForToRentName(String name) {
        log.debug("Getting reservations for toRent name: {}", name);
        return reservationRepository.getReservationsByToRentName(name);
    }

    @Override
    public Long newReservation(NewReservationForm newReservationForm) {
        checkReservationDate(newReservationForm.getToRent(), newReservationForm.getStartRentDate(), newReservationForm.getEndRentDate());

        Client tenant = clientService.getClientById(newReservationForm.getTenant());
        Client landlord = clientService.getClientById(newReservationForm.getLandlord());
        ToRent toRent = toRentService.getToRentById(newReservationForm.getToRent());

        Reservation reservation = Reservation.builder()
                .startRentDate(newReservationForm.getStartRentDate())
                .endRentDate(newReservationForm.getEndRentDate())
                .cost(newReservationForm.getCost())
                .tenant(tenant)
                .landlord(landlord)
                .toRent(toRent)
                .build();

        tenant.addToReservationsAsTenant(reservation);
        landlord.addToReservationsAsLandlord(reservation);
        toRent.addToReservations(reservation);

        log.debug("Saving reservation {}", reservation);
        Reservation save = reservationRepository.save(reservation);
        return save.getId();
    }

    @Override
    public Long changeReservation(ChangeReservationForm changeReservationForm) {
        checkReservationDate(changeReservationForm.getToRent(), changeReservationForm.getStartRentDate(), changeReservationForm.getEndRentDate());

        Client tenant = clientService.getClientById(changeReservationForm.getTenant());
        Client landlord = clientService.getClientById(changeReservationForm.getLandlord());
        ToRent toRent = toRentService.getToRentById(changeReservationForm.getToRent());

        Reservation reservation = getReservationById(changeReservationForm.getReservationId());
        reservation
                .setStartRentDate(changeReservationForm.getStartRentDate());
        reservation
                .setEndRentDate(changeReservationForm.getEndRentDate());
        reservation
                .setCost(changeReservationForm.getCost());
        reservation
                .setTenant(tenant);
        reservation
                .setLandlord(landlord);
        reservation
                .setToRent(toRent);

        tenant.addToReservationsAsTenant(reservation);
        landlord.addToReservationsAsLandlord(reservation);
        toRent.addToReservations(reservation);

        log.debug("Reservation changed {}", reservation);
        return reservation.getId();
    }


    private void checkReservationDate(Long newReservationForm, Date newReservationForm1, Date newReservationForm2) {
        if (!isReservationDateValid(
                newReservationForm,
                newReservationForm1,
                newReservationForm2)) {
            log.debug("Reservation date not valid");
            throw new IncorrectReservationDateException(
                    String.format("Reservation for toRent %d incorrect, start date %s, end date %s",
                            newReservationForm, newReservationForm1,
                            newReservationForm2));
        }
    }

    @Override
    public boolean isReservationDateValid(Long toRentId, Date startDate, Date endDate) {
        log.debug("Checking reservation date. toRent: {}, startDate: {}, endDate: {}", toRentId, startDate, endDate);
        Set<Reservation> reservations = reservationRepository.checkDate(toRentId, startDate, endDate);
        return reservations.isEmpty();
    }

    @Override
    public Reservation getReservationById(Long id) {
        log.debug("Getting toRent by id: {}", id);
        return reservationRepository.findById(id).orElseThrow(
                () -> new ReservationNotFoundException(String.format("Reservation %d not found", id)));
    }
}
