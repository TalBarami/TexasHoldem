package TexasHoldem.domain.game.hand;

import TexasHoldem.common.Exceptions.HandException;
import TexasHoldem.domain.game.card.Card;

import java.util.*;

public class HandCalculator {

    private static Card[][] getPossibleHands(List<Card> cards){
        Card[][] possibleHands = new Card[21][5];
        int cardsSelected = 0;
        int hand = 0;


        for(int firstCard = 0; firstCard < 7; firstCard++){
            // select first card not to be in the hand
            for(int secondCard = firstCard + 1; secondCard < 7; secondCard++){
                // every card that is not the first or second will added to the hand
                for(int i = 0; i < 7; i++){
                    if(i != firstCard && i != secondCard){
                        possibleHands[hand][cardsSelected++] = cards.get(i);
                    }
                }
                // next hand
                cardsSelected = 0;
                hand++;
            }
        }
        return possibleHands;
    }

    public static Hand getHand(List<Card> cards){
        Card[][] possibleHands = getPossibleHands(cards);
        Optional<Hand> hand = Arrays.stream(possibleHands)
                .map(h -> new Hand(Arrays.asList(h)))
                .max(Hand::compareTo);
        if(!hand.isPresent())
            throw new HandException();
        return hand.get();
    }

    public static Hand getHand(Card ... cards){
        return getHand(Arrays.asList(cards));
    }
}
