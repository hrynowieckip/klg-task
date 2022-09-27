package com.hrynowieckip.klgtask.controller.api;

import com.hrynowieckip.klgtask.domain.form.ChangeReservationForm;
import com.hrynowieckip.klgtask.domain.form.NewReservationForm;
import com.hrynowieckip.klgtask.domain.model.Reservation;
import com.hrynowieckip.klgtask.exception.IncorrectReservationDateException;
import com.hrynowieckip.klgtask.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> newReservation(@RequestBody @Valid NewReservationForm newReservationForm) {
        Long id = reservationService.newReservation(newReservationForm);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping
    public ResponseEntity<?> changeReservation(@RequestBody @Valid ChangeReservationForm changeReservationForm) {
        Long id = reservationService.changeReservation(changeReservationForm);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/tentant/{name}")
    public ResponseEntity<?> getReservationsForTenantName(@PathVariable String name) {
        Set<Reservation> reservations = reservationService.getReservationsForTenantName(name);
        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("/torent/{name}")
    public ResponseEntity<?> getReservationsForToRentName(@PathVariable String name) {
        Set<Reservation> reservations = reservationService.getReservationsForToRentName(name);
        return ResponseEntity.ok().body(reservations);
    }

    @ExceptionHandler(IncorrectReservationDateException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectDateException(
            IncorrectReservationDateException ex) {
        log.error("An exception occurred!", ex);
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
