package Server.domain.game.card;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Suit {
    CLUB, DIAMOND, HEART, SPADE
}
