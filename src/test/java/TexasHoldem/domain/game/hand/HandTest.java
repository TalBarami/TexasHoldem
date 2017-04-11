package TexasHoldem.domain.game.hand;

import TexasHoldem.common.JsonSerializer;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;


/**
 * Created by Tal on 08/04/2017.
 */
public class HandTest {
    private static List<Hand> hands;
    private static Logger logger = LoggerFactory.getLogger(HandTest.class);

    @BeforeClass
    public static void setUp()  {
        JsonSerializer json = new JsonSerializer();
        hands = Arrays.asList(json.readValue(new File("src\\test\\java\\TexasHoldem\\domain\\game\\hand\\hands.json"), Hand[].class));
        logger.info("Initializing done with the following hands: {}", hands);
    }

    @AfterClass
    public static void tearDown(){

    }

    @Test
    public void testTwoPair(){
        assertThat(hand(0).compareTo(hand(0)), is(0));
        assertThat(hand(0).compareTo(hand(1)), is(lessThan(0)));
    }

    @Test
    public void testStraightFlush(){
        assertThat(hand(2).compareTo(hand(3)), is(greaterThan(0)));
        assertThat(hand(2).compareTo(hand(1)), is(greaterThan(0)));
    }

    @Test
    public void testFourOfAKing(){
        assertThat(hand(4).compareTo(hand(4)), is(0));
        assertThat(hand(4).compareTo(hand(5)), is(lessThan(0)));
        assertThat(hand(4).compareTo(hand(1)), is(greaterThan(0)));
        assertThat(hand(6).compareTo(hand(7)), is(lessThan(0)));
    }

    @Test
    public void testHighCard(){
        assertThat(hand(8).compareTo(hand(9)), is(lessThan(0)));
    }

    private Hand hand(int index){
        return hands.get(index);
    }
}