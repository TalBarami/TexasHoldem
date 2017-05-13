package TexasHoldem.common.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class NoBalanceForBuyInException extends GameException {
    public NoBalanceForBuyInException(String message){
        super(message);
    }
}
