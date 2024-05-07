package egovk.domain;

import egovk.domain.*;
import egovk.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderRejected extends AbstractEvent {

    private String orderId;
    private String productId;
    private String productName;
    private Integer qty;
}
