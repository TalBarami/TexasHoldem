package TexasHoldem.domain.game.hand;


import TexasHoldem.domain.game.card.Card;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Tal on 05/04/2017.
 */
public class Hand implements Comparable<Hand> {
    private List<Card> hand;
    private Map<Integer, List<Card>> handGroup;
    private Category category;

    public Hand(List<Card> cards){
        hand = cards;
        Collections.sort(hand);
        handGroup = groupHand();
        calculate();
    }

    public Hand(Card ... cards){
        this(Arrays.asList(cards));
    }

    private Hand(){

    }


    private void calculate(){
        if(isStraightFlush())
            category = Category.STRAIGHT_FLUSH;
        else if(isFourOfAKind())
            category = Category.FOUR_OF_A_KIND;
        else if(isFullHouse())
            category = Category.FULL_HOUSE;
        else if(isFlush())
            category = Category.FLUSH;
        else if(isStraight())
            category = Category.STRAIGHT;
        else if(isThreeOfAKind())
            category = Category.THREE_OF_A_KIND;
        else if(isTwoPair())
            category = Category.TWO_PAIR;
        else if(isOnePair())
            category = Category.ONE_PAIR;
        else
            category = Category.HIGH_CARD;

    }

    @Override
    public int compareTo(Hand that) {
        if(this.category == that.category) {
            if(this.isSmallestStraight() || that.isSmallestStraight()){
                return this.hand.get(0).rank() - that.hand.get(0).rank();
            } else {
                List<List<Card>> dis = cardsComparator(this.handGroup);
                List<List<Card>> dat = cardsComparator(that.handGroup);

                for (int i = 0; i < dis.size(); i++) {
                    if (dis.get(i).get(0).rank() != dat.get(i).get(0).rank()) {
                        return dis.get(i).get(0).rank() - dat.get(i).get(0).rank();
                    }
                }
                return 0;
            }
        }
        else return this.category.ordinal() - that.category.ordinal();
    }

    private boolean isStraightFlush(){
        return isStraight() && isFlush();
    }

    private boolean isFlush(){
        int suit = hand.get(0).suit();
        for(Card card : hand){
            if(card.suit() != suit)
                return false;
        }
        return true;
    }

    private boolean isStraight(){
        int current = hand.get(0).rank();
        if(current == 14)
            current = 0;
        for(int i=1; i<hand.size(); i++){
            if(current + i != hand.get(i).rank())
                return false;
        }
        return true;
    }

    private boolean isSmallestStraight(){
        return isStraight() && hand.get(0).rank() == 14;
    }


    private boolean isFourOfAKind(){
        return handGroup.values().stream().filter(lst -> lst.size() == 4).count() == 1;
    }

    private boolean isFullHouse(){
        return isOnePair() && isThreeOfAKind();
    }

    private boolean isThreeOfAKind(){
        return handGroup.values().stream().filter(lst -> lst.size() == 3).count() == 1;
    }

    private boolean isTwoPair(){
        return handGroup.values().stream().filter(lst -> lst.size() == 2).count() == 2;
    }

    private boolean isOnePair(){
        return handGroup.values().stream().filter(lst -> lst.size() == 2).count() == 1;
    }

    private Map<Integer, List<Card>> groupHand() {
        Map<Integer, List<Card>> groups = new HashMap<>();
        List<Card> hand = new ArrayList<>(this.hand);
        hand.stream()
                .map(Card::rank)
                .distinct()
                .forEach(rank -> groups.put(rank, hand.stream()
                        .filter(card -> rank.equals(card.rank()))
                        .collect(Collectors.toList())));
        return groups;
    }

    enum Category{
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH

    }

    private List<List<Card>> cardsComparator(Map<Integer, List<Card>> handGroup) {
        List<List<Card>> result = new ArrayList<>(handGroup.values());
        result.sort((lst1, lst2) -> {
            if (lst1.size() == lst2.size())
                return lst1.get(0).rank() - lst2.get(0).rank();
            else return lst1.size() - lst2.size();
        });
        Collections.reverse(result);
        return result;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "hand=" + hand +
                ", handGroup=" + handGroup +
                ", category=" + category +
                '}';
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        Hand hand1 = (Hand) that;

        return hand.equals(hand1.hand) && handGroup.equals(hand1.handGroup) && category == hand1.category;
    }

    @Override
    public int hashCode() {
        int result = hand.hashCode();
        result = 31 * result + handGroup.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}