package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.PRECONDITION_FAILED)
public class NoBalanceForBuyInException extends GameException {
    public NoBalanceForBuyInException(String message){
        super(message);
    }
}
