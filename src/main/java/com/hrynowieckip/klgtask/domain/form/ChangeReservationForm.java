package com.hrynowieckip.klgtask.domain.form;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class ChangeReservationForm {
    @NotNull(message = "{changeReservationForm.reservationId.notNull}")
    private Long reservationId;
    @NotNull(message = "{changeReservationForm.startRentDate.notNull}")
    @Future(message = "{changeReservationForm.startRentDate.future}")
    private Date startRentDate;
    @NotNull(message = "{changeReservationForm.endRentDate.notNull}")
    @Future(message = "{changeReservationForm.endRentDate.future}")
    private Date endRentDate;
    @NotNull(message = "{changeReservationForm.cost.notNull}")
    private BigDecimal cost;

    @NotNull(message = "{changeReservationForm.tenant.notNull}")
    private Long tenant;
    @NotNull(message = "{changeReservationForm.landlord.notNull}")
    private Long landlord;

    @NotNull(message = "{changeReservationForm.toRent.notNull}")
    private Long toRent;
}
