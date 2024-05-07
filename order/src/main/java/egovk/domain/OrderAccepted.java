package egovk.domain;

import egovk.domain.*;
import egovk.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderAccepted extends AbstractEvent {

    private String orderId;
    private String productId;
    private String productName;
    private Integer qty;

    public OrderAccepted(Order aggregate) {
        super(aggregate);
    }

    public OrderAccepted() {
        super();
    }
}
//>>> DDD / Domain Event
