package egovk.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class AcceptOrderCommand {

    private String productName;
    private String qty;
}
