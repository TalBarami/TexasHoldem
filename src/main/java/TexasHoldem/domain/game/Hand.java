package TexasHoldem.domain.game;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Tal on 05/04/2017.
 */
public class Hand implements Comparable<Hand> {
    private List<Card> cards;
    private Category category;

    public Hand(List<Card> cards){
        this.cards = cards;
        Collections.sort(cards);
        Collections.reverse(cards);
    }

    public void calculateCategory(){
        List<Card> handCards = new ArrayList<>();
    }

    @Override
    public int compareTo(Hand o) {
        return 0;
    }

    public double score(){
        return 0;
    }

    private boolean isFlush(){
        return cards.get(0).suit() == cards.get(cards.size()).suit();
    }

    private boolean isStraight(){
        int current = cards.get(0).rank();
        for(int i=0; i<cards.size(); i++){
            if(current + i != cards.get(i).rank())
                return false;
        }
        return true;
    }

    private void check(){

    }

    enum Category{
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH
    }
}