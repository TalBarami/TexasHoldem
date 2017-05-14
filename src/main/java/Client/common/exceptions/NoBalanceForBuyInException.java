package Client.common.exceptions;

import TexasHoldem.common.Exceptions.GameException;

public class NoBalanceForBuyInException extends GameException {
    public NoBalanceForBuyInException(String message){
        super(message);
    }
}
