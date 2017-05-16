package TexasHoldem.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by rotemwald on 16/05/17.
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Transaction {
    DEPOSIT, WITHDRAW
}
