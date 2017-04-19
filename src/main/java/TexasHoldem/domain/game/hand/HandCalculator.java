package TexasHoldem.domain.game.hand;

import TexasHoldem.common.Exceptions.HandException;
import TexasHoldem.domain.game.card.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class HandCalculator {
    private static Logger logger = LoggerFactory.getLogger(HandCalculator.class);

    private static Card[][] getPossibleHands(List<Card> cards){
        Card[][] possibleHands = new Card[21][5];
        int cardsSelected = 0;
        int hand = 0;


        for(int firstCard = 0; firstCard < 7; firstCard++){
            for(int secondCard = firstCard + 1; secondCard < 7; secondCard++){
                for(int i = 0; i < 7; i++){
                    if(i != firstCard && i != secondCard){
                        possibleHands[hand][cardsSelected++] = cards.get(i);
                    }
                }
                cardsSelected = 0;
                hand++;
            }
        }
        return possibleHands;
    }

    public static Hand getHand(List<Card> cards){
        if(cards.size() != 7) {
            logger.error("Hand size is not 7. received: {}.", cards);
            throw new HandException();
        }
        logger.debug("Received a set of the following cards: {}", cards);
        Card[][] possibleHands = getPossibleHands(cards);
        Optional<Hand> optHand = Arrays.stream(possibleHands)
                .map(h -> new Hand(Arrays.asList(h)))
                .max(Hand::compareTo);
        if(!optHand.isPresent()) {
            logger.error("An error occurred while attempting to calculate the maximal hand.");
            throw new HandException();
        }
        Hand hand = optHand.get();
        logger.debug("Returns the following hand: {}", hand);
        return hand;
    }

    public static Hand getHand(Card ... cards){
        return getHand(Arrays.asList(cards));
    }
}
