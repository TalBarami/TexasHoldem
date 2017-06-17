package Server.domain.game.card;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Suit {
    CLUBS, DIAMONDS, HEATS, SPADES
}
