package com.hrynowieckip.klgtask.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrynowieckip.klgtask.domain.form.ChangeReservationForm;
import com.hrynowieckip.klgtask.domain.form.NewReservationForm;
import com.hrynowieckip.klgtask.domain.model.Reservation;
import com.hrynowieckip.klgtask.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
//@AutoConfigureMockMvc
class ReservationControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ReservationService reservationService;

    String name;
    Reservation reservation;
    Long id;

    NewReservationForm newReservationForm;
    ChangeReservationForm changeReservationForm;

    @BeforeEach
    void setUp() {
        name = "Test";
        id = 10L;
        reservation = new Reservation();
        reservation.setId(id);

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
    }

    @Test
    void newReservation() throws Exception {
        when(reservationService.newReservation(newReservationForm)).thenReturn(id);
        mvc.perform(post("/api/v1/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newReservationForm)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(reservationService).newReservation(newReservationForm);
    }

    @Test
    void changeReservation() throws Exception {
        when(reservationService.changeReservation(changeReservationForm)).thenReturn(id);
        mvc.perform(put("/api/v1/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeReservationForm)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(reservationService).changeReservation(changeReservationForm);
    }

    @Test
    void getReservationsForToRentName() throws Exception {
        when(reservationService.getReservationsForToRentName(name)).thenReturn(Set.of(reservation));
        mvc.perform(get("/api/v1/reservation/torent/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getReservationsForTenantName() throws Exception {
        when(reservationService.getReservationsForTenantName(name)).thenReturn(Set.of(reservation));
        mvc.perform(get("/api/v1/reservation/tentant/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
