package egovk.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RejectOrderCommand {

    private String orderId;
}
