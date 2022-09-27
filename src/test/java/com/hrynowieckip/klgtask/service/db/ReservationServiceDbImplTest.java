package com.hrynowieckip.klgtask.service.db;

import com.hrynowieckip.klgtask.domain.form.ChangeReservationForm;
import com.hrynowieckip.klgtask.domain.form.NewReservationForm;
import com.hrynowieckip.klgtask.domain.model.Client;
import com.hrynowieckip.klgtask.domain.model.Reservation;
import com.hrynowieckip.klgtask.domain.model.ToRent;
import com.hrynowieckip.klgtask.domain.repository.ReservationRepository;
import com.hrynowieckip.klgtask.exception.IncorrectReservationDateException;
import com.hrynowieckip.klgtask.exception.ReservationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceDbImplTest {
    @Mock
    ReservationRepository repository;
    @Mock
    ClientServiceDbImpl clientService;
    @Mock
    ToRentServiceDbImpl toRentService;
    @InjectMocks
    ReservationServiceDbImpl service;

    Long id;
    Reservation reservation;
    Long toRentId;
    Date startDate;
    Date endDate;
    NewReservationForm newReservationForm;
    ChangeReservationForm changeReservationForm;
    Client client;
    ToRent toRent;

    @BeforeEach
    void setUp() {
        id = 1L;
        toRentId = 2L;
        startDate = new Date(2014, 02, 11);
        endDate = new Date(2012, 04, 12);
        newReservationForm = new NewReservationForm();
        newReservationForm.setLandlord(1L);
        newReservationForm.setTenant(1L);
        newReservationForm.setToRent(1L);
        newReservationForm.setCost(BigDecimal.ONE);
        newReservationForm.setStartRentDate(new Date(2020, 10, 10));
        newReservationForm.setEndRentDate(new Date(2020, 10, 11));

        changeReservationForm = new ChangeReservationForm();
        changeReservationForm.setLandlord(1L);
        changeReservationForm.setTenant(1L);
        changeReservationForm.setToRent(1L);
        changeReservationForm.setCost(BigDecimal.ONE);
        changeReservationForm.setStartRentDate(new Date(2020, 10, 10));
        changeReservationForm.setEndRentDate(new Date(2020, 10, 11));
        changeReservationForm.setReservationId(2L);

        client = new Client();
        client.setReservationsAsLandlord(Set.of());
        client.setReservationsAsTenant(Set.of());

        toRent = new ToRent();
        toRent.setReservations(Set.of());

        reservation = new Reservation();
        reservation.setLandlord(client);
        reservation.setTenant(client);
        reservation.setToRent(toRent);
        reservation.setCost(BigDecimal.ONE);
        reservation.setStartRentDate(new Date(2020, 10, 10));
        reservation.setEndRentDate(new Date(2020, 10, 11));
    }

    @Test
    void newReservation_addingReservation() {
        when(clientService.getClientById(1L)).thenReturn(client);
        when(toRentService.getToRentById(1L)).thenReturn(toRent);

        when(repository.save(any())).thenReturn(reservation);

        service.newReservation(newReservationForm);

        verify(clientService, times(2)).getClientById(anyLong());
        verify(toRentService).getToRentById(anyLong());
        verify(repository).save(any());
    }

    @Test
    void newReservation_incorrectDate() {
        when(repository.checkDate(newReservationForm.getToRent(), newReservationForm.getStartRentDate(),
                newReservationForm.getEndRentDate())).thenReturn(Set.of(new Reservation()));

        assertThrows(IncorrectReservationDateException.class, () -> service.newReservation(newReservationForm));
        verify(clientService, never()).getClientById(anyLong());
        verify(toRentService, never()).getToRentById(anyLong());
        verify(repository, never()).findById(anyLong());
    }

    @Test
    void changeReservation() {
        when(clientService.getClientById(1L)).thenReturn(client);
        when(toRentService.getToRentById(1L)).thenReturn(toRent);
        when(repository.findById(changeReservationForm.getReservationId())).thenReturn(Optional.of(reservation));

        service.changeReservation(changeReservationForm);

        verify(clientService, times(2)).getClientById(anyLong());
        verify(toRentService).getToRentById(anyLong());
        verify(repository).findById(changeReservationForm.getReservationId());
    }

    @Test
    void isReservationDateValid_returnTrue() {
        when(repository.checkDate(toRentId, startDate, endDate)).thenReturn(Set.of());
        boolean reservationDateValid = service.isReservationDateValid(toRentId, startDate, endDate);

        assertTrue(reservationDateValid);
        verify(repository).checkDate(toRentId, startDate, endDate);
    }

    @Test
    void isReservationDateValid_returnFalse() {
        when(repository.checkDate(toRentId, startDate, endDate)).thenReturn(Set.of(new Reservation()));
        boolean reservationDateValid = service.isReservationDateValid(toRentId, startDate, endDate);

        assertFalse(reservationDateValid);
        verify(repository).checkDate(toRentId, startDate, endDate);
    }

    @Test
    void getReservationById_returnsReservation() {
        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        Reservation reservationById = service.getReservationById(id);

        assertEquals(reservation, reservationById);
        verify(repository).findById(id);
    }

    @Test
    void getReservationById_throwException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> service.getReservationById(id));
        verify(repository).findById(id);
    }
}
