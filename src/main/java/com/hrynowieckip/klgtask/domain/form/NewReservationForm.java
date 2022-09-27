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
public class NewReservationForm {
    @NotNull(message = "{newReservationForm.startRentDate.notNull}")
    @Future(message = "{newReservationForm.startRentDate.future}")
    private Date startRentDate;
    @NotNull(message = "{newReservationForm.endRentDate.notNull}")
    @Future(message = "{newReservationForm.endRentDate.future}")
    private Date endRentDate;
    @NotNull(message = "{newReservationForm.cost.notNull}")
    private BigDecimal cost;

    @NotNull(message = "{newReservationForm.tenant.notNull}")
    private Long tenant;
    @NotNull(message = "{newReservationForm.landlord.notNull}")
    private Long landlord;

    @NotNull(message = "{newReservationForm.toRent.notNull}")
    private Long toRent;
}
